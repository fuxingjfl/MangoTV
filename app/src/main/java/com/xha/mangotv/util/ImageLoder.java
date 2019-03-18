package com.xha.mangotv.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.xha.mangotv.R;

import java.io.File;

/**
 * Created by Mr赵 on 2018/4/18.
 */

public class ImageLoder {
    public static void info(Context context){
        File cacheDir = StorageUtils.getCacheDirectory(context);  //指定默认的硬盘(sd卡)的缓存文件夹路径

        /**
         * 可以自己创建一个sd下面的文件夹作为硬盘缓存的路径
         */
        File file = new File(Environment.getExternalStorageDirectory(),"image");
        if (! file.exists()){
            file.mkdirs();
        }

        //1.创建一个ImageLoaderConfiguration,,,图片加载器的配置对象
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)

                .threadPoolSize(3) // default_tp  线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2) // default_tp 设置当前线程的优先级
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default_tp
                .denyCacheImageMultipleSizesInMemory()

                //1 TB = 1024GB 1G=1024MB 1M = 1024KB 1KB = 1024BYTE
                //LruMemoryCache,,lru最近最少使用算法...内部维护的是LinkedHashMap,,当一张图片最近很少使用的时候会从mao集合里面移除
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024)) //指定内存缓存的大小,,2M,,可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)  // 内存缓存的最大值
                .memoryCacheSizePercentage(13) // default_tp

                //指定硬盘/磁盘缓存的路径
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default_tp 可以自定义缓存路径
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb sd卡(本地)缓存的最大值
                .diskCacheFileCount(100)  // 可以缓存的文件数量


                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default_tp
                .writeDebugLogs() // 打印debug log
                .build(); //开始构建

        //对上面配置对象的初始化
        ImageLoader.getInstance().init(config);
    }
    /*
    * 展示图片
    * */
    public static DisplayImageOptions getDefaultOption() {
        DisplayImageOptions options = new DisplayImageOptions.Builder().
               showImageOnLoading(R.drawable.ic_launcher) // 设置图片下载期间显示的图片
//                .showImageForEmptyUri(R.mipmap.ic_launcher) // 设置图片Uri为空或是错误的时候显示的图片
//                .showImageOnFail(R.mipmap.ic_launcher) */// 设置图片加载或解码过程中发生错误显示的图片

                .resetViewBeforeLoading(true)  // default_tp 设置图片在加载前是否重置、复位

                .cacheInMemory(true) // default_tp  设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // default_tp  设置下载的图片是否缓存在SD卡中

                .considerExifParams(true) // default_tp


                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default_tp 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565) // default_tp 设置图片的解码类型

                .displayer(new SimpleBitmapDisplayer()) // default_tp  还可以设置圆角图片new RoundedBitmapDisplayer(20)

                .build();

        return options;
    }
}
