package com.xha.mangotv.okhttpUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.xha.mangotv.util.ConstantValues;
import com.xha.mangotv.util.MD5Utils;

/**
 * OkHttpClient 工具类
 * 
 * @author lsf
 * 
 */
public class OkHttpClientManager {
	/**
	 * 请求时间
	 */
	private static int TIMES = 30;
	public final static int CONNECT_TIMEOUT = 60;
	public final static int READ_TIMEOUT = 100;
	public final static int WRITE_TIMEOUT = 60;
	private static OkHttpClientManager mInstance;
	private static OkHttpClient mOkHttpClient;
	private Handler mDelivery;
	
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	public OkHttpClientManager() {
		mOkHttpClient = new OkHttpClient();
		// 获取UI主线程
		mDelivery = new Handler(Looper.getMainLooper());
	}

	public static OkHttpClientManager getInstance() {
		if (mInstance == null) {
			synchronized (OkHttpClientManager.class) {
				if (mInstance == null) {
					mInstance = new OkHttpClientManager();
				}
			}
		}
		return mInstance;
	}

	/**
	 * 同步的Get请求
	 * 
	 * @param url
	 * @return Response
	 */
	public Response _getOkHttp(String url) throws IOException {
		final Request request = new Builder().url(url).build();
		Call call = mOkHttpClient.newCall(request);
		Response execute = call.execute();
		return execute;
	}

	/**
	 * 同步的Get请求
	 * 
	 * @param url
	 * @return 字符串
	 */
	public String _getAsString(String url) throws IOException {
		Response execute = _getOkHttp(url);
		return execute.body().string();
	}

	/**
	 * 异步的get请求
	 * 
	 * @param url
	 * @param callback
	 */
	public void _getOkHeadHttp(String url, final int code,
			HashMap<String, String> map, final HttpCallback callback) {
		Builder mBuilder = new Builder();
		mBuilder.addHeader("apikey", "97e8d621474eea52233fd6a9f2b8727b");
		deliveryResult(callback, code, mBuilder
				.url(Constants.WAI_WebName + url).post(mapToParam(map)).build());
	}
	
	
	/**
	 * 异步的get请求
	 * 
	 * @param url
	 * @param callback
	 */
	public void _getOkHttp(String url, final int code,
			HashMap<String, String> map, final HttpCallback callback) {
		Builder mBuilder = new Builder();
		deliveryResult(callback, code, mBuilder
				.url(Constants.WAI_WebName + url).post(mapToParam(map)).build());
	}

	/**
	 * 设置缓存的大小
	 * 
	 * @param cacheSize
	 */
	/*
	 * public void setCacheSize(Context context, int maxSize) { // TODO
	 * Auto-generated method stub File cacheDirectory = new File("cache"); if
	 * (!cacheDirectory.exists()) { cacheDirectory.mkdirs(); } Cache cache = new
	 * Cache(cacheDirectory, cacheSize); mOkHttpClient.setCache(cache);
	 * mOkHttpClient.setCache(new Cache(context.getExternalCacheDir()
	 * .getAbsoluteFile(), maxSize)); }
	 */

	/**
	 * 异步的get请求
	 * 
	 * @param url
	 * @param callback
	 */
	public void _getOkHttp(String url, final int code,
			HashMap<String, String> map, final HttpCallback callback, int Times) {
		Builder mBuilder = new Builder();
		mOkHttpClient.setConnectTimeout(Times, TimeUnit.MINUTES);
		deliveryResult(callback, code, getRequest(mBuilder, url, map, callback));
	}
	
	
	/**
	 * 异步的get请求json
	 * 
	 * @param url
	 * @param callback
	 */
	public void _getOkHttp(String url, final int code,
			String json, final HttpCallback callback, int Times) {
		Builder mBuilder = new Builder();
		mOkHttpClient.setReadTimeout(READ_TIMEOUT, TimeUnit.SECONDS);//设置读取超时时间
		mOkHttpClient.setWriteTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);//设置写的超时时间
		mOkHttpClient.setConnectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);//设置连接超时时间
		deliveryResult(callback, code, getRequest(mBuilder, url, json, callback));
	}
	
	private Request getRequest(Builder mBuilder, String url,
			String json, HttpCallback callback) {
		return mBuilder.url(url).post(RequestBody.create(JSON , json)).build();
	}

	private Request getRequest(Builder mBuilder, String url,
			HashMap<String, String> map, HttpCallback callback) {
		return mBuilder.url(url).post(mapToParam(map)).build();
	}

	private RequestBody mapToParam(HashMap<String, String> map) {
		// TODO Auto-generated method stub

		FormEncodingBuilder builder = new FormEncodingBuilder();
		for (Entry<String, String> entry : map.entrySet()) {
			builder.add(entry.getKey(), entry.getValue());
		}
		return builder.build();
	}

	private void deliveryResult(final HttpCallback callback, final int code,
			Request request) {
		SendStart(code, callback);
		mOkHttpClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(final Request request, final IOException e) {
				SendFailure(code, callback);
			}

			@Override
			public void onResponse(final Response response) {
				String string;
				try {
					string = response.body().string();
					SendSuccess(code, string, callback);
					System.out.println("response: " + response);
					System.out.println("response.cacheResponse: "
							+ response.cacheResponse());
					System.out.println("response.networkResponse() "
							+ response.networkResponse());
					ResetOption();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					SendFailure(code, callback);
				}

			}
		});
	}
	
	private void deliveryResult(
			Request request) {
		try {
			Response response=mOkHttpClient.newCall(request).execute();
			//判断请求是否成功
			if(response.isSuccessful()){
			    //打印服务端返回结果
			    Log.i("TAG",response.body().string());
			    
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 重置设置
	 */
	public void ResetOption() {
		// TODO Auto-generated method stub
		/**
		 * 超时重置
		 */
		if (mOkHttpClient.getConnectTimeout() == TIMES) {
		} else {
			mOkHttpClient.setConnectTimeout(TIMES, TimeUnit.SECONDS);
		}

		/*	*//**
		 * 缓存重置
		 */
		/*
		 * try { mOkHttpClient.getCache().getSize();
		 * mOkHttpClient.setCache(null); } catch (Exception e) { // TODO: handle
		 * exception }
		 */

	}

	/**
	 * 开始
	 * 
	 * @param code
	 * @param callback
	 */
	public void SendStart(final int code, final HttpCallback callback) {
		// TODO Auto-generated method stub
		mDelivery.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				callback.onStart(code);
			}
		});
	}

	/**
	 * 成功
	 * 
	 * @param code
	 *
	 * @param callback
	 */
	public void SendSuccess(final int code, final String result,
			final HttpCallback callback) {
		// TODO Auto-generated method stub
		mDelivery.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				callback.onSuccess(result, code);
			}
		});
	}

	/**
	 * 结束
	 * 
	 * @param code
	 * @param callback
	 */
	public void SendFailure(final int code, final HttpCallback callback) {
		// TODO Auto-generated method stub
		mDelivery.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				callback.onFailure(code);
			}
		});
	}

//	/**
//     * okHttp post同步请求
//     * @param actionUrl  接口地址
//     * @param paramsMap   请求参数
//     */
//    public void requestPostBySyn(String actionUrl, HashMap<String, String> paramsMap) {
//        try {
////            //处理参数
////            StringBuilder tempParams = new StringBuilder();
////            int pos = 0;
////            for (String key : paramsMap.keySet()) {
////                if (pos > 0) {
////                    tempParams.append("&");
////                }
////                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
////                pos++;
////            }
//            //补全请求地址
//            String requestUrl = ConstantValues.URI_YONG_TEST+"Project/lists";
//            String data=ConstantValues.KEY+"{\"rl\":\"\",\"zt\":\"\",\"city\":\"\",\"page\":\"\"}";
//			String md5data = MD5Utils.getMD5(data);
//            //生成参数
//            String params = "{\"rl\":\"\",\"zt\":\"\",\"city\":\"\",\"page\":\"\"},\"checkcode\":\""+md5data+"\"}";
//            //创建一个请求实体对象 RequestBody
//            RequestBody body = RequestBody.create(JSON, params.getBytes());
//            //创建一个请求
//            final Request request = addHeaders().url(requestUrl).post(body).build();
//            //创建一个Call
//            final Call call = mOkHttpClient.newCall(request);
//            //执行请求
//            Response response = call.execute();
//            //请求执行成功
//            if (response.isSuccessful()) {
//                //获取返回数据 可以是String，bytes ,byteStream
//                Log.e("TAG", "response ----->" + response.body().string());
//            }
//        } catch (Exception e) {
//            Log.e("TAG", e.toString());
//        }
//    }
    
    
//    /**
//     * okHttp post异步请求
//     * @param actionUrl 接口地址
//     * @param paramsMap 请求参数
//     * @param callBack 请求返回数据回调
//     * @param <T> 数据泛型
//     * @return
//     */
//    public <T> Call requestPostByAsyn(String actionUrl, HashMap<String, String> paramsMap, final ReqCallBack<T> callBack) {
//        try {
////            StringBuilder tempParams = new StringBuilder();
////            int pos = 0;
////            for (String key : paramsMap.keySet()) {
////                if (pos > 0) {
////                    tempParams.append("&");
////                }
////                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
////                pos++;
////            }
//        	String data=ConstantValues.KEY+"{\"rl\":\"\",\"zt\":\"\",\"city\":\"\",\"page\":\"\"}";
// 			String md5data = MD5Utils.getMD5(data);
//             //生成参数
//             String params = "{\"rl\":\"\",\"zt\":\"\",\"city\":\"\",\"page\":\"\"},\"checkcode\":\""+md5data+"\"}";
//            RequestBody body = RequestBody.create(JSON, params);
//
//
//            String requestUrl = ConstantValues.URI_YONG_TEST+"Project/lists";
//            final Request request = addHeaders().url(requestUrl).put(body).build();
//            final Call call = mOkHttpClient.newCall(request);
//            call.enqueue(new Callback() {
//
//				@Override
//				public void onFailure(Request arg0, IOException arg1) {
//					// TODO Auto-generated method stub
//					 failedCallBack("访问失败", callBack);
//	                    Log.e("TAG",arg1.toString());
//				}
//
//				@Override
//				public void onResponse(Response arg0) throws IOException {
//					// TODO Auto-generated method stub
//					if (arg0.isSuccessful()) {
//                        String string = arg0.body().string();
//                        Log.e("TAG", "response ----->" + string);
//                    } else {
//                        failedCallBack("服务器错误", callBack);
//                    }
//				}
//            });
//            return call;
//        } catch (Exception e) {
//            Log.e("TAG", e.toString());
//        }
//        return null;
//    }
    
    
//    /**
//     * post请求 提交数据到服务器
//     *
//     */
//    public void httpPostJSON(){
//    	String data=ConstantValues.KEY+"{\"rl\":\"\",\"zt\":\"\",\"city\":\"\",\"page\":\"\"}";
//			String md5data = MD5Utils.getMD5(data);
//			md5data=md5data.toLowerCase();
//         //生成参数
//         String json = "{\"data\":{\"rl\":\"\",\"zt\":\"\",\"city\":\"\",\"page\":\"\"},\"checkcode\":\""+md5data+"\"}";
//        MediaType JSON = MediaType.parse("application/json; charset=UTF-8");
//        //换成自己的ip就行
//        String url = ConstantValues.URI_YONG_TEST+"Project/lists.html";
//        OkHttpClient client = new OkHttpClient();//创建okhttp实例
//        RequestBody body=RequestBody.create(JSON,json);
//        Request request = new Builder()
//                .url(url)
//                .post(body)
//                .build();
//        Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//
//			@Override
//			public void onResponse(Response arg0) throws IOException {
//				// TODO Auto-generated method stub
//				if (arg0.isSuccessful()) {
//                    Log.i("TAG", "onResponse: " + arg0.body().string());
//                }
//			}
//
//			@Override
//			public void onFailure(Request arg0, IOException arg1) {
//				// TODO Auto-generated method stub
//				 Log.i("TAG", "onFailure: " + arg1);
//			}
//		});
//    }
    
    
    /**
     * 统一处理失败信息
     * @param errorMsg
     * @param callBack
     * @param <T>
     */
    private <T> void failedCallBack(final String errorMsg, final ReqCallBack<T> callBack) {
    	mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onReqFailed(errorMsg);
                }
            }
        });
    }
    public interface ReqCallBack<T> {
        /**
         * 响应成功
         */
         void onReqSuccess(T result);

        /**
         * 响应失败
         */
         void onReqFailed(String errorMsg);
    }
    /**
     * 统一为请求添加头信息
     * @return
     */
    private static Builder addHeaders() {
        Builder builder = new Builder()
//                .addHeader("Connection", "keep-alive")
//                .addHeader("platform", "2")
//                .addHeader("phoneModel", Build.MODEL)
//                .addHeader("systemVersion", Build.VERSION.RELEASE)
//                .addHeader("appVersion", "3.2.0");
        .addHeader("content-type", "text/html;charset:utf-8") 
        .addHeader("user-agent", "android")  ;
        return builder;
    }
	
	public interface HttpCallback {
		/**
		 * 
		 * @param result
		 *            回调参数
		 * @param code
		 *            回调标记位
		 */
		abstract void onSuccess(String result, int code);

		/**
		 * 
		 * @param code
		 *            回调标记位
		 */
		abstract void onStart(int code);

		/**
		 * 
		 * @param code
		 *            回调标记位
		 */
		abstract void onFailure(int code);

	}

}
