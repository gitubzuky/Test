#include <jni.h>
#include <string>
#include <sstream>
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
    string hello = "from C++ in NDK：" + num;
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL Java_com_lib_mylib_util_JniUtil_stringFromJNI2(
        JNIEnv *env,
        jobject /* this */) {
        string hello = "from C++ in NDK：2";
        return env->NewStringUTF(hello.c_str());
}