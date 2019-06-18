# AudioLibs

声明：该项目从网上复制了大神代码，自己稍加修改便于使用，侵权必删


仿微信一键录音，长按录音、上划取消、时间太短提示。注意录音权限和读写权限的获取


[![](https://jitpack.io/v/ForMuHan/AudioLibs.svg)](https://jitpack.io/#ForMuHan/AudioLibs)


Step 1. 在project下 build.gradle 添加

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. 在项目中添加依赖

	dependencies {
	        implementation 'com.github.ForMuHan:AudioLibs:V1.0.1'
	}
  
Step 3. 在布局中添加控件
  <?xml version="1.0" encoding="utf-8"?>
  <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
      android:layout_width="match_parent"
      android:layout_height="match_parent">

      <com.syw.audio.widget.AudioRecordButton
          android:id="@+id/arb"
          android:layout_width="match_parent"
          android:layout_height="40dp"
          android:layout_alignParentBottom="true"
          android:background="@mipmap/ic_launcher"
          android:longClickable="true"
          android:text="录音按钮" />

  </RelativeLayout>
  
  Step 4. 在activity中监听
  
  	arb = findViewById(R.id.arb);
  
        arb.setAudioFinishRecorderListener(new AudioRecordButton.AudioFinishRecorderListener() {
	
            @Override
            public void onFinished(float seconds, String filePath) {
                Log.e("syw","filePath:"+filePath);
            }

            @Override
            public void onNormal() {
                Log.e("syw","onNormal:");
            }

            @Override
            public void onRecord() {
                Log.e("syw","onRecord:");
            }

            @Override
            public void onCancel() {
                Log.e("syw","onCancel:");
            }

            @Override
            public void onShort() {
                Log.e("syw","onShort:");
            }
        });
        
  
