#ifndef __CSTATS__
#define __CSTATS__

#include <jni.h>

enum EBASES {NONE = 0, SINGLE, DOUBLE, TRIPLE, HOMERUN, MAX };

struct SStats
{
	int* Stats;
	int atbats;
	int hits;
	int singles;
	int doubles;
	int triples;
	int homeruns;
	int walks;
	int sacflys;
	int sacbunts;

	float battingAvg;
	float OBP;
	float slugging;
	float OPS;
	float TotalBases;

	void initStats();
	void calculateAllStats();
}*stats;

extern "C"
{
	JNIEXPORT jfloatArray JNICALL Java_com_StarStudios_PlayerData_CStats_Calculate(JNIEnv * env, jobject jobj, jintArray allStats);

}

#endif
