/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class vgl_natives_memory_VGLMemory */

#ifndef _Included_vgl_natives_memory_VGLMemory
#define _Included_vgl_natives_memory_VGLMemory
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     vgl_natives_memory_VGLMemory
 * Method:    __nvglmemAllocByte
 * Signature: (I)Ljava/nio/ByteBuffer;
 */
JNIEXPORT jobject JNICALL Java_vgl_natives_memory_VGLMemory__1_1nvglmemAllocByte
  (JNIEnv *, jclass, jint);

/*
 * Class:     vgl_natives_memory_VGLMemory
 * Method:    __nvglmemFree
 * Signature: (Ljava/nio/Buffer;)V
 */
JNIEXPORT void JNICALL Java_vgl_natives_memory_VGLMemory__1_1nvglmemFree
  (JNIEnv *, jclass, jobject);

/*
 * Class:     vgl_natives_memory_VGLMemory
 * Method:    __nvglbyteArray
 * Signature: (I)[B
 */
JNIEXPORT jbyteArray JNICALL Java_vgl_natives_memory_VGLMemory__1_1nvglbyteArray
  (JNIEnv *, jclass, jint);

/*
 * Class:     vgl_natives_memory_VGLMemory
 * Method:    __nvglshortArray
 * Signature: (I)[S
 */
JNIEXPORT jshortArray JNICALL Java_vgl_natives_memory_VGLMemory__1_1nvglshortArray
  (JNIEnv *, jclass, jint);

/*
 * Class:     vgl_natives_memory_VGLMemory
 * Method:    __nvglcharArray
 * Signature: (I)[C
 */
JNIEXPORT jcharArray JNICALL Java_vgl_natives_memory_VGLMemory__1_1nvglcharArray
  (JNIEnv *, jclass, jint);

/*
 * Class:     vgl_natives_memory_VGLMemory
 * Method:    __nvglintArray
 * Signature: (I)[I
 */
JNIEXPORT jintArray JNICALL Java_vgl_natives_memory_VGLMemory__1_1nvglintArray
  (JNIEnv *, jclass, jint);

/*
 * Class:     vgl_natives_memory_VGLMemory
 * Method:    __nvglfloatArray
 * Signature: (I)[F
 */
JNIEXPORT jfloatArray JNICALL Java_vgl_natives_memory_VGLMemory__1_1nvglfloatArray
  (JNIEnv *, jclass, jint);

/*
 * Class:     vgl_natives_memory_VGLMemory
 * Method:    __nvgllongArray
 * Signature: (I)[J
 */
JNIEXPORT jlongArray JNICALL Java_vgl_natives_memory_VGLMemory__1_1nvgllongArray
  (JNIEnv *, jclass, jint);

/*
 * Class:     vgl_natives_memory_VGLMemory
 * Method:    __nvgldoubleArray
 * Signature: (I)[D
 */
JNIEXPORT jdoubleArray JNICALL Java_vgl_natives_memory_VGLMemory__1_1nvgldoubleArray
  (JNIEnv *, jclass, jint);

#ifdef __cplusplus
}
#endif
#endif
