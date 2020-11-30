#include <jni.h>
#include <string>
#include "googe_code_sm3/code.c"



char* jstringTostring(JNIEnv* env, jstring jstr)
{
    char* rtn = NULL;
    jclass clsstring = env->FindClass("java/lang/String");
    jstring strencode = env->NewStringUTF("utf-8");
    jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray)env->CallObjectMethod(jstr, mid, strencode);
    jsize alen = env->GetArrayLength(barr);
    jbyte* ba = env->GetByteArrayElements(barr, JNI_FALSE);
    if (alen > 0)
    {
        rtn = (char*)malloc(alen + 1);
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    env->ReleaseByteArrayElements(barr, ba, 0);
    return rtn;
}


/***
 *  goole 验证器 sm3实现算法
 * time (int) (System.currentTimeMillis()/1000)
 * num   6或者8
 * pwd   25971966bac6e7c0dedcf1082a6ed266
 */
extern "C" JNIEXPORT jstring JNICALL
Java_utils_kkutils_jni_GoogleCode_stringFromJNI(
        JNIEnv* env,
        jobject o,jint time,jint num,jstring pwd) {
    std::string hello = "Hello from C++";

    int c=getCode(time,num,jstringTostring(env,pwd));
    return env->NewStringUTF(std::to_string(c).c_str());
}

