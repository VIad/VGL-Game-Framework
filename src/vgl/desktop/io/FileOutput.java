package vgl.desktop.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class FileOutput {

	// TODO
	// private static File currentlyOpened;
	//
	// public static void openFile(File file) {
	// currentlyOpened = file;
	// }

	public static void writeStringUTF8(String contents, File destination) {
		new vgl.desktop.tools.async.VoidWorker<String>(contents, (s) -> {
			try {
				FileOutputStream fos;
				OutputStreamWriter osr = new OutputStreamWriter(
				        fos = new FileOutputStream(destination),
				        StandardCharsets.UTF_8);
				osr.write(s);
				osr.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
	}

	public static void writeString(String contents, File destination) {
		new vgl.desktop.tools.async.VoidWorker<String>(contents, (s) -> {
			try {
				FileWriter writer = new FileWriter(destination);
				writer.write(s);
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
	}

	public static void writeBytes(byte[] bytes, File destination) {
		new vgl.desktop.tools.async.VoidWorker<byte[]>(bytes, (b) -> {
			try {
				FileOutputStream fos = new FileOutputStream(destination);
				fos.write(bytes);
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
	}

	public static void writeBytes(ByteBuffer bytes, File destination) {
		writeBytes(bytes.array(), destination);
	}

}
