#include <stdio.h>
//#include <mem.h>
#include <math.h>
#include "SM3.c"

unsigned int  floorDiv( unsigned int  x,  unsigned int  y) {
    unsigned int r = x / y;
    // if the signs are different and modulo not zero, round down
    if ((x ^ y) < 0 && (r * y != x)) {
        r--;
    }
    return r;
}

unsigned int floorMod( unsigned int  x,  unsigned int  y) {
    return x - floorDiv(x, y) * y;
}

void hex_str_to_byte(char *hex_str, int length, unsigned char *result) {
    char h, l;
    for (int i = 0; i < length / 2; i++) {
        if (*hex_str < 58) {
            h = *hex_str - 48;
        } else if (*hex_str < 71) {
            h = *hex_str - 55;
        } else {
            h = *hex_str - 87;
        }
        hex_str++;
        if (*hex_str < 58) {
            l = *hex_str - 48;
        } else if (*hex_str < 71) {
            l = *hex_str - 55;
        } else {
            l = *hex_str - 87;
        }
        hex_str++;
        *result++ = h << 4 | l;
    }
}

int getCode(unsigned int time,int num,char  * pwd) {

    unsigned char X = 30;

    unsigned int T0 = time, T;

    int D = num;

    unsigned char sm3_in[20] = {0};

    unsigned char ausTokenID[16] = {0};

    hex_str_to_byte(pwd,32,ausTokenID);

    unsigned char S[20];

    unsigned char P0[32];

    unsigned int I1, I2, I3, I4, I5, I6, I7, I8, I;

    unsigned int P;

    T = T0 / X;

    unsigned char *p = 0;

//    if (big_endian())
//
//        T = big_to_small_32(T);

    p = (unsigned char *) &T;

    sm3_in[0] = *(p + 3);

    sm3_in[1] = *(p + 2);

    sm3_in[2] = *(p + 1);

    sm3_in[3] = *(p + 0);

    memcpy(sm3_in + 4, ausTokenID, 16);
    SM3_256(sm3_in, 20, P0);


//    printf("\n ausTokenID    ");
//    for (int i = 0; i < 16; i++) {
//        printf("%d ", ausTokenID[i]);
//    }
//
//    printf("\n PO    ");
//    for (int i = 0; i < 32; i++) {
//        printf("%d ", P0[i]);
//    }

    I1 = P0[0] << 24 | P0[1] << 16 | P0[2] << 8 | P0[3];

    I2 = P0[4] << 24 | P0[5] << 16 | P0[6] << 8 | P0[7];

    I3 = P0[8] << 24 | P0[9] << 16 | P0[10] << 8 | P0[11];

    I4 = P0[12] << 24 | P0[13] << 16 | P0[14] << 8 | P0[15];

    I5 = P0[16] << 24 | P0[17] << 16 | P0[18] << 8 | P0[19];

    I6 = P0[20] << 24 | P0[21] << 16 | P0[22] << 8 | P0[23];

    I7 = P0[24] << 24 | P0[25] << 16 | P0[26] << 8 | P0[27];

    I8 = P0[28] << 24 | P0[29] << 16 | P0[30] << 8 | P0[31];


    I = (I1 + I2 + I3 + I4 + I5 + I6 + I7 + I8);// MOD 2^32

    I = floorMod(I, pow(2,32));
    P = I % ((unsigned  int)pow(10,D)); // 10的D次方
    printf("\nresult  %06d\n", P);//可能不足位数，实际使用的时候需要格式化一下 比如 1611038220 433556575255443555543333584A5A33  结果是 1213



    return P;
}
