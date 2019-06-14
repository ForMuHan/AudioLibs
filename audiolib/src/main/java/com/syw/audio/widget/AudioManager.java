package com.syw.audio.widget;

import android.media.MediaRecorder;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

//录音核心类
public class AudioManager {

    private MediaRecorder mRecorder;
    //文件夹位置
//    private String mDirString;
    //录音文件保存路径
    private String mCurrentFilePathString;
    //是否真备好开始录音
    private boolean isPrepared;

    /**
     * 单例化这个类
     */
    private static AudioManager mInstance;

    private AudioManager() {
    }

    public static AudioManager getInstance() {
        if (mInstance == null) {
            synchronized (AudioManager.class) {
                if (mInstance == null) {
                    mInstance = new AudioManager();

                }
            }
        }
        return mInstance;

    }

    /**
     * 回调函数，准备完毕，准备好后，button才会开始显示录音
     */
    public interface AudioStageListener {
        void wellPrepared();
    }

    public AudioStageListener mListener;

    public void setOnAudioStageListener(AudioStageListener listener) {
        mListener = listener;
    }

    // 准备方法
    public void prepareAudio() {
        // 一开始应该是false的
        isPrepared = false;
        //创建所属文件夹
        File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "audioLibs");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileNameString = generalFileName();
        File file = new File(dir, fileNameString);
        //获取文件
        mCurrentFilePathString = file.getAbsolutePath();

        mRecorder = new MediaRecorder();
        // 设置输出文件
        mRecorder.setOutputFile(file.getAbsolutePath());
        // 设置meidaRecorder的音频源是麦克风
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // 设置文件音频的输出格式为amr
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        // 设置音频的编码格式为amr。这里采用AAC主要为了适配IOS，保证在IOS上可以正常播放。
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        // 严格遵守google官方api给出的mediaRecorder的状态流程图
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mRecorder.start();
        // 准备结束
        isPrepared = true;
        // 已经准备好了，可以录制了
        if (mListener != null) {
            mListener.wellPrepared();
        }
    }


    /**
     * 随机生成文件的名称
     * @return
     */
    private String generalFileName() {
        return System.currentTimeMillis() + ".amr";
    }

    // 获得声音的level
    public int getVoiceLevel(int maxLevel) {
        if (isPrepared && null != mRecorder) {
            try {
                int ratio = mRecorder.getMaxAmplitude() / 600;
                int db = 0;// 分贝
                if (ratio > 1) {
                    db = (int) (20 * Math.log10(ratio));
                }
                int level = 1;
                switch (db / 4) {
                    case 0:
                        level = 1;
                        break;
                    case 1:
                        level = 2;
                        break;
                    case 2:
                        level = 3;
                        break;
                    case 3:
                        level = 4;
                        break;
                    case 4:
                        level = 5;
                        break;
                    case 5:
                        level = 6;
                        break;
                    default:
                        level = 7;
                        break;
                }
                return level;

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return 1;
    }

    // 释放资源
    public void release() {
        // 严格按照api流程进行
        if (mRecorder == null) return;
        /*
         * 这里处理一些特定情况下的异常。2017/04/12 by wgyscsf
         */
        try {
            //下面三个参数必须加，不加的话会奔溃，在mediarecorder.stop();
            //报错为：RuntimeException:stop failed
//            mRecorder.setOnErrorListener(null);
//            mRecorder.setOnInfoListener(null);
//            mRecorder.setPreviewDisplay(null);
            mRecorder.stop();
        } catch (IllegalStateException e) {
            Log.i("Exception", Log.getStackTraceString(e) + "123");
        } catch (RuntimeException e) {
            Log.i("Exception", Log.getStackTraceString(e) + "123");
        } catch (Exception e) {
            Log.i("Exception", Log.getStackTraceString(e) + "123");
        }
//        mRecorder.release();
        mRecorder = null;

    }

    // 取消,因为prepare时产生了一个文件，所以cancel方法应该要删除这个文件，
    // 这是与release的方法的区别
    public void cancel() {
        release();
        if (mCurrentFilePathString != null) {
            File file = new File(mCurrentFilePathString);
            file.delete();
            mCurrentFilePathString = null;
        }
    }

    public String getCurrentFilePath() {
        return mCurrentFilePathString;
    }

}
