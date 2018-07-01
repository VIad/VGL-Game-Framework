package vgl.core.gfx;

import vgl.core.buffers.MemoryBufferInt;
import vgl.core.exception.VGLGraphicsException;
import vgl.core.exception.VGLRuntimeException;
import vgl.tools.IResource;

/**
 * Currently takes an insane amount of memory, optimisation required ASAP
 */
public class Image implements IResource{

	private int width, height, loadedWidth, loadedHeight;
	
	private boolean closed;
	
	private Image.Format dataFormat;
	
	public static enum Format{
		RGBA,
		
		ARGB,
		
		RGB
	}
	
	private MemoryBufferInt pixels;
	
	public Image(int width, int height) {
		this.dataFormat = Image.Format.RGBA;
		this.width = width;
		this.height = height;
		this.loadedWidth = width;
		this.loadedHeight = height;
		this.pixels = new MemoryBufferInt(width * height);
	}
	
	public Image(int width, int height, Image.Format dataFormat) {
		this.dataFormat = dataFormat;
		this.width = width;
		this.height = height;
		this.loadedWidth = width;
		this.loadedHeight = height;
		this.pixels = new MemoryBufferInt(width * height);
	}
	

	public MemoryBufferInt getPixels() {
		if(closed)
			throw new IllegalStateException("All resources allocated with this image object have been released");
		return pixels;
	}
	
	public Image.Format getDataFormat() {
		if(closed)
			throw new IllegalStateException("All resources allocated with this image object have been released");
		return dataFormat;
	}
	
	public Image setPixel(int x, int y, Color pixel) {
		if(closed)
			throw new IllegalStateException("All resources allocated with this image object have been released");
		checkIfInRegion(x, y, 0, 0);
		setPixelFormat0(x, y, pixel);
		return this;
	}
	
	public static Image copy(Image src, Image.Format format, boolean destroySrc) {
		Image image = new Image(src.width, src.height, format);
		for(int y = 0; y < src.getHeight(); y++) {
			for(int x = 0; x < src.getWidth(); x++) {
				image.setPixel(x, y, src.getPixel(x, y));
			}
		}
		if(destroySrc)
			src.releaseMemory();
		return image;
	}
	
	private void setPixelFormat0(int x, int y, Color pixel) {
		if(closed)
			throw new IllegalStateException("All resources allocated with this image object have been released");
		int bufferSource = -0x1;
		switch(dataFormat) {
			case ARGB:
				bufferSource = (width * y + x);
				pixels.put(bufferSource, pixel.getARGB());
				break;
			case RGB:
				bufferSource = (width * y + x);
				pixels.put(bufferSource, pixel.getRGB());
				break;
			case RGBA:
				bufferSource = (width * y + x);
				pixels.put(bufferSource, pixel.getRGBA());
				break;
			default:
				throw new VGLGraphicsException("Unrecognised image format or null");
		}
	}

	public Color getPixel(int x, int y) {
		if(closed)
			throw new IllegalStateException("All resources allocated with this image object have been released");
		return getPixelFormat0(x, y);
	}
	
	private Color getPixelFormat0(int x, int y) {
		if(closed)
			throw new IllegalStateException("All resources allocated with this image object have been released");
		switch (dataFormat)
		{
			case ARGB:
				return Color.fromARGB(pixels.readAbsolute((width * y + x)));
			case RGB:
				return new Color(pixels.readAbsolute((width * y + x)));
			case RGBA:
				return new Color(pixels.readAbsolute((width * y + x)), true);
			default:
				return null;
		}	
	}
	
	public int getWidth() {
		if(closed)
			throw new IllegalStateException("All resources allocated with this image object have been released");
		return width;
	}
	
	public int getHeight() {
		if(closed)
			throw new IllegalStateException("All resources allocated with this image object have been released");
		return height;
	}
	
	public Image createSubImage(int x, int y, int width, int height) {
		if(closed)
			throw new IllegalStateException("All resources allocated with this image object have been released");
		checkIfInRegion(x,y,width,height);
		Image image = new Image(width, height, dataFormat);
		copyRegion(x, y, width, height, this, image);
		return image;
	}
	
	public static Image fromColor(int width, int height, Color color) {
		Image image = new Image(width, height, color.hasTransparency() ?
				                                     Image.Format.ARGB 
				                                   : Image.Format.RGB);
		for(int y = 0; y < height; y ++) {
			for(int x = 0; x < width; x ++) {
				image.setPixel(x, y, color);
			}
		}
		return image;
	}
	
	public static Image fromColor(int width, int height, Color color, float alpha) {
		Image image = new Image(width, height, Image.Format.ARGB);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				image.setPixel(x, y, new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
			}
		}
		return image;
	}
	
	public static void copyRegion(int x, int y, int width, int height, Image source, Image dest) {
		for(int yy = 0; yy < height; yy++) {
			for(int xx = 0; xx < width; xx++) {
				dest.setPixel(xx, yy, source.getPixel(xx + x, yy + y));
			}
		}
	}

	private void checkIfInRegion(int x, int y, int w, int h) {
		if (x + w > this.width || y + h > this.height || x < 0 || y < 0 || w < 0 || h < 0)
			throw new VGLRuntimeException("The given parameters exceeed the original image's bounds >> "+this);
	}

	@Override
	public String toString() {
		if(closed)
			throw new IllegalStateException("All resources allocated with this image object have been released");
		return "Image [width=" + width + ", height=" + height + ", loadedWidth=" + loadedWidth + ", loadedHeight="
		        + loadedHeight +", format="+dataFormat+ ", bytes= "+pixels.byteCapacity()+"]";
	}
	
	public void releaseMemory() {
		if(closed)
			throw new IllegalStateException("All resources allocated with this image object have been released");
		closed = true;
		this.pixels.getBuffer().free();
		this.pixels = null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (closed ? 1231 : 1237);
		result = prime * result + ((dataFormat == null) ? 0 : dataFormat.hashCode());
		result = prime * result + height;
		result = prime * result + loadedHeight;
		result = prime * result + loadedWidth;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Image other = (Image) obj;
		if (closed != other.closed)
			return false;
		if (dataFormat != other.dataFormat)
			return false;
		if (height != other.height)
			return false;
		if (loadedHeight != other.loadedHeight)
			return false;
		if (loadedWidth != other.loadedWidth)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

	@Override
	public boolean isDisposed() {
		return closed;
	}
	
	

}
