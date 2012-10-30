#include "CStats.h"
#include <android/log.h>

#define  LOG_TAG    "Calculations"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)


void SStats::initStats()
{
	atbats = Stats[0];
	hits = Stats[1];
	singles = Stats[2];
	doubles = Stats[3];
	triples = Stats[4];
	homeruns = Stats[5];
	walks = Stats[6];
	sacflys = Stats[7];
	sacbunts = Stats[8];
}
void SStats::calculateAllStats()
{
	if(atbats > 0)
		battingAvg = (float)hits / (float)atbats;
	else atbats = 0;

	int nBottomOBP = atbats + walks + sacflys + sacbunts;
	if(nBottomOBP > 0)
		OBP = hits + walks / (float)nBottomOBP;
	else OBP = 0;

	TotalBases = (SINGLE * singles) + (DOUBLE * doubles) + (TRIPLE * triples) + (HOMERUN * homeruns);

	if(atbats > 0)
	{
		slugging = (float)TotalBases / (float)atbats;
	}
	else slugging = 0;

	OPS = OBP + slugging;

}
JNIEXPORT jfloatArray JNICALL Java_com_StarStudios_PlayerData_CStats_Calculate(JNIEnv * env, jobject obj, jintArray allStats)
{
	stats = new SStats();

	jsize arrSize = (*env).GetArrayLength(allStats);
	jint* jarr = (*env).GetIntArrayElements(allStats, false);
	stats->Stats = new int[arrSize];
	for(int i = 0; i < arrSize; i++)
	{
		stats->Stats[i] = jarr[i];
	}
	stats->initStats();

	LOGI("Stats inited");

	stats->calculateAllStats();

	jfloat avgs[5] = {stats->battingAvg, stats->OBP, stats->slugging, stats->OPS, stats->TotalBases};

	jfloatArray averages = env->NewFloatArray(5);

	env->SetFloatArrayRegion(averages, 0, 5, avgs);

	return averages;
}
