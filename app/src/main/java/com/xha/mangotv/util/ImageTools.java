package com.xha.mangotv.util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;

/**
 *Title:
 *Description:处理图片的工具类
 *
 *Copyright:Copyright (c) 2016
 *Company:cnTomorrow
 *@author 方爱�?
 *@time 2016/12/27 12:50
 */
public final class ImageTools {

	/**
	 *Title:
	 *Description:压缩图片方法
	 *
	 *Copyright:Copyright (c) 2016
	 *Company:cnTomorrow
	 *@author 方爱�?
	 *@time 2016/12/28 18:18
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
			return newbmp;
	}


	
	/**
	 *Title:
	 *Description:判断sd卡的存在
	 *
	 *Copyright:Copyright (c) 2016
	 *Company:cnTomorrow
	 *@author 方爱�?
	 *@time 2016/12/27 12:58
	 */
	public static boolean checkSDCardAvailable(){
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
	

	
	/**
	 *Title:
	 *Description:将图片保存到sd�?
	 *
	 *Copyright:Copyright (c) 2016
	 *Company:cnTomorrow
	 *@author 方爱�?
	 *@time 2016/12/27 12:59
	 */
	public static void savePhotoToSDCard(Bitmap photoBitmap,String path,String photoName){
		//判断SD卡是否存�?
		if (checkSDCardAvailable()) {
			File dir = new File(path);
			//如果文件夹存在，不创建，否则创建文件�?
			if (!dir.exists()){
				dir.mkdirs();
			}
			
			File photoFile = new File(dir.getPath() , photoName + ".png");
			FileOutputStream fileOutputStream = null;
			Log.i("photoFile的路径是啥呢�?",photoFile.getAbsolutePath());
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				//判断bitmap是否为空
				if (photoBitmap != null) {
					if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
						fileOutputStream.flush();
//						fileOutputStream.close();
					}
				}
			} catch (FileNotFoundException e) {
				photoFile.delete();
				e.printStackTrace();
			} catch (IOException e) {
				photoFile.delete();
				e.printStackTrace();
			} finally{
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} 
	}


	
	/**
	 *Title:
	 *Description:将所有图片从sd卡删�?
	 *
	 *Copyright:Copyright (c) 2016
	 *Company:cnTomorrow
	 *@author 方爱�?
	 *@time 2016/12/27 13:00
	 */
	public static void deleteAllPhoto(String path){
		if (checkSDCardAvailable()) {
			File folder = new File(path);
			File[] files = folder.listFiles();
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
		}
	}
	
	
	/**
	 *Title:
	 *Description:将所有图片从sd卡删�?
	 *
	 *Copyright:Copyright (c) 2016
	 *Company:cnTomorrow
	 *@author 方爱�?
	 *@time 2016/12/27 13:00
	 */
	public static void deletePhoto(String path){
		if (checkSDCardAvailable()) {
			File folder = new File(path);
				folder.delete();
		}
	}

}
