package vgl.web;

import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.typedarrays.client.Uint8ArrayNative;
import com.google.gwt.typedarrays.shared.ArrayBuffer;
import com.google.gwt.typedarrays.shared.ArrayBufferView;
import com.google.gwt.typedarrays.shared.Float32Array;
import com.google.gwt.typedarrays.shared.Uint8Array;
import com.vgl.gwtreq.client.GWTDataView;

import vgl.core.buffers.MemoryBuffer;
import vgl.core.exception.VGLFatalError;
import vgl.core.exception.VGLIOException;
import vgl.core.gfx.Color;
import vgl.core.gfx.Image;
import vgl.main.VGL;
import vgl.platform.io.FileDetails;
import vgl.tools.functional.callback.BinaryCallback;
import vgl.tools.functional.callback.Callback;

abstract public class WebSpecific {
	
	public static class DataFormatStruct{
		public ArrayBufferView view;
		public int glFormat;
	}
	
	abstract public static class FileLoading{
		
		public static void loadImage0(FileDetails file, BinaryCallback<Image, Throwable> result) {
			VGL.io
			   .readBytes(file, buffer -> {
				   JS.getImage(JS.cast(buffer),
						   img -> result.invoke(img, null),
						   error -> result.invoke(null, new VGLIOException("ImageIO error : "+error)));
			   });
		}
		
	}

	abstract public static class JS{
		
		public static void jsLoadedCallback(ImageData pixels, int width, int height, int oWidth, int oHeight,
		        Callback<Image> onComplete) {
			Image image = new Image(oWidth, oHeight);

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++)
					image.setPixel(x, y,
					        new Color(
					                pixels.getRedAt(x, y) / 255f,
					                pixels.getGreenAt(x, y) / 255f,
					                pixels.getBlueAt(x, y) / 255f,
					                pixels.getAlphaAt(x, y) / 255f));
			}

			onComplete.invoke(image);
		}
		
		public static native void getImage(ArrayBuffer memory, Callback<Image> success, Callback<String> error) /*-{
	    var arrayBufferView = new Uint8Array(memory);
	    var blob = new Blob([arrayBufferView], {type: "image/jpeg"});
	    var urlCreator = $wnd.URL || $wnd.webkitURL || $wnd.mozURL;
	    var imageUrl = urlCreator.createObjectURL(blob);
	    var img = $doc.createElement("img");
	    img.src = imageUrl;
	    img.style.display = "none";
	    function isPowerOfTwo(x)
	    {
	        return (x & (x - 1)) == 0;
	    }
	    function nextHighestPowerOfTwo(x)
	    {
	        --x;
	        for (var i = 1; i < 32; i <<= 1)
	        {
	            x = x | x >> i;
	        }
	        return x + 1;
	    }
	    img.onload = function ()
	    {
	        var canvas = $doc.createElement("canvas");
	        //canvas.width = isPowerOfTwo(img.width) ? img.width : nextHighestPowerOfTwo(img.width);
	       //canvas.height = isPowerOfTwo(img.height) ? img.height : nextHighestPowerOfTwo(img.height);
	       canvas.width = img.width;
	       canvas.height = img.height;
	        var ctx = canvas.getContext("2d");
	        ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
	        var pix = ctx.getImageData(0, 0, canvas.width, canvas.height);
	        $doc.body.removeChild(img);
	        @vgl.web.WebSpecific.JS::jsLoadedCallback(*)(pix, canvas.width, canvas.height,
	            img.width, img.height, success);
	    };
	    img.onerror = function(e){
	    	error.@vgl.tools.functional.callback.Callback::invoke(*)("unable to get image");
	    }
	    $doc.body.appendChild(img);
	}-*/;
		
		public static DataFormatStruct toArrayBuffer(Image image) {
			DataFormatStruct obj = new DataFormatStruct();
			if(image.getDataFormat() == Image.Format.ARGB ||
			   image.getDataFormat() == Image.Format.RGBA) {
				obj.view = Uint8ArrayNative.create(image.getPixels().byteCapacity());
//				WebSpecific.Memory
//				           .copyInto(image.getPixels().getBuffer(),
//				        		    obj.view);
			}
			return null;
		}
		
		public static ArrayBufferView copy(MemoryBuffer pixels) {
			Uint8Array array = Uint8ArrayNative.create(pixels.capacity());
			for(int i = 0; i < pixels.capacity(); i++) {
				array.set(i, pixels.readByte(i));
			}
			return array;
		}
		
		public static ArrayBuffer cast(MemoryBuffer buffer) {
			if(buffer.getClass() != WebMemoryBuffer.class)
				throw new VGLFatalError("This buffer is not supported by web platform");
			return ((GWTDataView) buffer.nativeBufferDetails().getBuffer()).getView().buffer();
		}
		
	}
	
	abstract public static class Memory{
		
		public static enum Type{
			UINT8,
			INT8,
			UINT32,
			INT32,
			FLOAT32
		}
		
		public static void copyInto(MemoryBuffer src, ArrayBufferView dest, Memory.Type memType) {
			switch (memType)
			{
				case FLOAT32:
					Float32Array arr = (Float32Array) dest;
					
					break;
				case INT32:
					break;
				case INT8:
					break;
				case UINT32:
					break;
				case UINT8:
					break;
				default:
					break;
				
			}
		}
		
	}
	
}
