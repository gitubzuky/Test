#include <jni.h>
#include <string>
#include <sstream>
#include <iostream>
using namespace std;
extern "C"
JNIEXPORT jstring JNICALL Java_com_lib_mylib_util_JniUtil_stringFromJNI1(
        JNIEnv *env,
        jobject /* this */,
        jint position) {
    string num = "";
    stringstream ss;
    ss << position;
    num = ss.str();
    string hello = "java调用了一波c++代码，参数：" + num;
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT void JNICALL Java_com_lib_mylib_util_JniUtil_stringFromJNI2(
        JNIEnv *env,
        jobject This,
        jint position) {
    jclass jniClass = env->FindClass("com/lib/mylib/util/JniUtil");
//    jmethodID jmethodID1 = env->GetMethodID(jniClass, "callByCpp", "()V");
    jmethodID jmethodID1 = env->GetMethodID(jniClass, "callByCpp", "(I)V");
    env->CallVoidMethod(This, jmethodID1, position);
}