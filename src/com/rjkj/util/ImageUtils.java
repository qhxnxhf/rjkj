package com.rjkj.util;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import net.coobird.thumbnailator.Thumbnails;


public class ImageUtils {
	/**
	 * allowed image extensions.
	 */
	private static final String[] ALLOWED_EXT = { "gif", "jpeg", "jpg", "png",
			"psd", "bmp", "tiff", "tif", "swc", "jpc", "jp2", "jpx", "jb2",
			"xbm", "wbmp" };
	
	private static final int MAX_BUFF_SIZE = 1024;
	
	private static Logger logger = Logger.getLogger(ImageUtils.class);

	public static boolean saveUploadImage(File srcFile, File destFile,
			int maxLength, boolean compressImage){
		// 如果图片压缩不等于真，直接拷贝文件
		if (compressImage != true) {
			try {
				org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
				return true;
			} catch (IOException e) {
				logger.error("copyFile error ", e);
				return false;
			}
		}
		// 根据图片最大边长，等比例计算另一边长
		try {
			BufferedImage bi = ImageIO.read(srcFile);
			if (bi.getHeight() <= maxLength && bi.getWidth() <= maxLength) {
				org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
				return true;
			} else {
				double xyScale = bi.getWidth()*1.0f / bi.getHeight();// x/y的比例
				int x = 0;
				int y = 0;
				if (bi.getHeight() >= bi.getWidth()) {
					y = maxLength;
					x = (int) (xyScale * maxLength);
				} else {
					x = maxLength;
					y = (int) (maxLength/xyScale);
				}
				createResizedImage(srcFile, destFile, x, y, 1);
			}

		} catch (Exception e) {
			logger.error("compressImage error ", e);
			return false;
		}
		return true;
	}
	
	/**
	 * Creates image file with fixed width and height.
	 * 
	 * @param sourceFile
	 *            input file
	 * @param destFile
	 *            file to save
	 * @param width
	 *            image width
	 * @param height
	 *            image height
	 * @param quality
	 *            image quality
	 * @throws IOException
	 *             when error occurs.
	 */
	public static void createResizedImage(final File sourceFile,
			final File destFile, final int width, final int height,
			final float quality) throws IOException {

		BufferedImage image = ImageIO.read(sourceFile);
		Dimension dimension = new Dimension(width, height);
		if (image.getHeight() == dimension.height && image.getWidth() == dimension.width) {
			writeUntouchedImage(sourceFile, destFile);
		} else {
			resizeImage(image, dimension.width, dimension.height, quality,destFile);

		}
	}
	
	/**
	 * writes unchanged file to disk.
	 * 
	 * @param sourceFile
	 *            - file to read from
	 * 
	 * @param destFile
	 *            - file to write to
	 * 
	 * @throws IOException
	 *             when error occurs.
	 */
	private static void writeUntouchedImage(final File sourceFile,
			final File destFile) throws IOException {
		FileInputStream fileIS = new FileInputStream(sourceFile);
		writeUntouchedImage(fileIS, destFile);
	}

	/**
	 * writes unchanged file to disk.
	 * 
	 * @param stream
	 *            - stream to read the file from
	 * 
	 * @param destFile
	 *            - file to write to
	 * 
	 * @throws IOException
	 *             when error occurs.
	 */
	private static void writeUntouchedImage(final InputStream stream,
			final File destFile) throws IOException {
		ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
		byte[] buffer = new byte[MAX_BUFF_SIZE];
		int readNum = -1;
		while ((readNum = stream.read(buffer)) != -1) {
			byteArrayOS.write(buffer, 0, readNum);
		}
		byte[] bytes = byteArrayOS.toByteArray();
		byteArrayOS.close();
		FileOutputStream fileOS = new FileOutputStream(destFile);
		fileOS.write(bytes);
		fileOS.flush();
		fileOS.close();
	}
	
	/**
	 * Resizes the image and writes it to the disk.
	 * 
	 * @param sourceImage
	 *            orginal image file.
	 * @param width
	 *            requested width
	 * @param height
	 *            requested height
	 * @param quality
	 *            requested destenation file quality
	 * @param destFile
	 *            file to write to
	 * @throws IOException
	 *             when error occurs.
	 */
	private static void resizeImage(final BufferedImage sourceImage,
			final int width, final int height, final float quality,
			final File destFile) throws IOException {
		try {
			Thumbnails.of(sourceImage).size(width, height).keepAspectRatio(
					false).outputQuality(quality).toFile(destFile);
			// for some special files outputQuality couses error:
			// IllegalStateException inner Thumbnailator jar. When exception is
			// thrown
			// image is resized without quality
			// When http://code.google.com/p/thumbnailator/issues/detail?id=9
			// will be fixed this try catch can be deleted. Only:
			// Thumbnails.of(sourceImage).size(width,
			// height).keepAspectRatio(false)
			// .outputQuality(quality).toFile(destFile);
			// should remain.
		} catch (IllegalStateException e) {
			Thumbnails.of(sourceImage).size(width, height).keepAspectRatio(
					false).toFile(destFile);
		}
	}

}
