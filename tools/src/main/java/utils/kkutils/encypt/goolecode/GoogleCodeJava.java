package utils.kkutils.encypt.goolecode;

//import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;

import utils.kkutils.encypt.Sm3;
//import java.util.Arrays;

public class GoogleCodeJava {
		//判断是否大端
	    public static boolean IsBigEndian()
	    {
			if(ByteOrder.nativeOrder()==ByteOrder.BIG_ENDIAN)
			{
				return true;
	    	}
			else 
			{
				return false;
			}
	    }
	    //判断是否小端
	    public static boolean IsLittleEndian()
	    {
	    	return !IsBigEndian();
	    }
	    //
	    public static int LeftRotate(int x,int n)
	    {
	    	return (x<<n)|(x>>(32-n)); 
	    }
	    //字符反转
	    public static int Reverse32(int x)
	    {
	    	return ((x & 0x000000ff)<<24)| ((x & 0x0000ff00)<<8) | ((x & 0x00ff0000)>>8) | ((x & 0xff000000)>>24);	
	    }
	    
	    public static int ML(byte X,short j)
	    {
	    		if(IsBigEndian())
	    		{
	    			return (int)(X<<(j%32));
	    		}
	    		else
	    		{
	    			return Reverse32((int)(X<<(j%32)));
	    		}
	    }

	    public static int  SUM(int X,int Y)
	    {
	    		if(IsBigEndian())
	    		{
	    				return (X+Y);	
	    		}
	    		else
	    		{
	    				return Reverse32(Reverse32(X)+Reverse32(Y));
	    		}	
	    }
	    
	   public static byte [] byteMerger(byte [] bt1,byte [] bt2)
	   { 
		   byte [] bt3 = new byte[bt1.length+bt2.length];
		   System.arraycopy(bt1, 0, bt3, 0, bt1.length);
		   System.arraycopy(bt2, 0, bt3, bt1.length,bt2.length);
		   return bt3;
	   }
	   
	   public static byte [] stringTo16Byte(String temp)
	   {
		   int len=temp.length();
		   for(int i=0;i<16-len;i++)
		   {
			   if(temp.length()==16)
			   {
				   break;
			   }
			   temp=temp+"0";
		   }
		   return temp.getBytes();
	   }
	    
	   public static byte[] intToBytes(int num)
	   {
		   	byte[] b=new byte[4];
		   	for(int i=0;i<4;i++)
		   	{
		   		b[i]=(byte)(num>>>(24-i*8));
		   	}
		   	return b;
	   }
	   //21447901   大端模式
	   public static byte [] intTobytes(int num)
	   {
		   byte[] b=new byte[4];
		   b[0]=(byte)(num>>24&0xff);
		   b[1]=(byte)(num>>16&0xff);
		   b[2]=(byte)(num>>8&0xff);
		   b[3]=(byte)(num&0xff);
		   return b;
	   }
	   // 小端模式
	   public static byte [] intTobytesl(int num)
	   {
		   byte[] b=new byte[4];
		   b[3]=(byte)(num>>24&0xff);
		   b[2]=(byte)(num>>16&0xff);
		   b[1]=(byte)(num>>8&0xff);
		   b[0]=(byte)(num&0xff);
		   return b;
	   }
	   
	   public static byte [] int2bytes(final int integer)
	   {
		   int buteNum=(40-Integer.numberOfLeadingZeros(integer<0?~integer:integer))/8;
		   byte [] byteArray=new byte[4];
		   for(int n=0;n<buteNum;n++)
		   {
			   byteArray[3-n]=(byte)(integer>>>(n*8));
		   }
		   return byteArray;
	   }
	   
	   public static byte hexStrToByte(String hexbytein)
	   {
		   return (byte)Integer.parseInt(hexbytein, 16);
	   }
	   
	   public static byte [] Str2Hex(String hexStrIn)
	   {
		   int hexlen=hexStrIn.length()/2;
		   byte [] result;
		   result =new byte[hexlen];
		   for(int i=0;i<hexlen;i++)
		   {
			   result[i]=hexStrToByte(hexStrIn.substring(i*2,i*2+2));
		   }
		   return result;
	   }
	   
	   public static byte [] hex2Bytes(String hex)
	   {
		   char [] hexChars=hex.toCharArray();
		   byte [] b=new byte[hexChars.length/2];
		   for(int i=0;i<b.length;i++)
		   {
			   b[i]=(byte)Integer.parseInt(""+hexChars[i*2]+hexChars[i*2+1],16);
		   }
		   return b;
	   }
	   
	   private static byte char2byte(char c)
	   {
		 return (byte)"0123456789ABCDEF".indexOf(c);  
	   }
	   
	   public static byte [] str2bytes(String s)
	   {
		   s=s.toUpperCase();
		   int l=s.length()/2;
		   char [] strchar=s.toCharArray();
		   byte [] b=new byte[l];
		   for(int i=0;i<l;i++)
		   {
			   int in=i*2;
			   b[i]=(byte)(char2byte(strchar[in])<<4|char2byte(strchar[in+1]));
		   }
		   return b;
	   }
	   
	   public static long floorDiv(long x,long y)
	   {
		   long r=x/y;
		   //System.out.println("r:" + r);
		   //System.out.println("x:" + x);
		   //System.out.println("y:" + y);
		   if((x^y) < 0 && (r*y!=x))
		   {
			   //System.out.println("r:" + r);
			   r--;
		   }
		   //System.out.println("r:" + r);
		   return r;
	   }
	   
	   public static long floorMod(long x,long y)
	   {
		   return x-floorDiv(x,y)*y;
	   }
	   
	   public static int bytes2int(byte [] b)
	   {
		   int v=0;
		   v=((b[3]&0xff)<<24) |((b[2]&0xff)<<16)|((b[1]&0xff)<<8)|(b[0]&0xff);
		   return v;
	   }
	   
	   public static Integer  byte2Integer(byte b)
	   {
		   return 0xff&b;
	   }
	    
	   public static long TruncateSM3(byte [] target)
	   {
		   int s1 = (target[0]&0xff) << 24| (target[1]&0xff)  << 16| (target[2]&0xff)  << 8| (target[3]&0xff);
		   int s2 = (target[4]&0xff) << 24| (target[5]&0xff)  << 16| (target[6]&0xff)  << 8| (target[7]&0xff);
		   int s3 = (target[8]&0xff) << 24| (target[9]&0xff)  << 16| (target[10]&0xff) << 8| (target[11]&0xff);
		   int s4 = (target[12]&0xff)<< 24| (target[13]&0xff) << 16| (target[14]&0xff) << 8| (target[15]&0xff);
		   int s5 = (target[16]&0xff)<< 24| (target[17]&0xff) << 16| (target[18]&0xff) << 8| (target[19]&0xff);
		   int s6 = (target[20]&0xff)<< 24| (target[21]&0xff) << 16| (target[22]&0xff) << 8| (target[23]&0xff);
		   int s7 = (target[24]&0xff)<< 24| (target[25]&0xff) << 16| (target[26]&0xff) << 8| (target[27]&0xff);
		   int s8 = (target[28]&0xff)<< 24| (target[29]&0xff) << 16| (target[30]&0xff) << 8| (target[31]&0xff);
		   int opt=s1+s2+s3+s4+s5+s6+s7+s8;
		   /*System.out.println("s1:" + s1);
		   System.out.println("s2:" + s2);
		   System.out.println("s3:" + s3);
		   System.out.println("s4:" + s4);
		   System.out.println("s5:" + s5);
		   System.out.println("s6:" + s6);
		   System.out.println("s7:" + s7);
		   System.out.println("s8:" + s8);*/
		   long x=opt&0x0FFFFFFFFl;
		   //System.out.println("x:" + x);
	       x= floorMod(x,(long)Math.pow(2, 32));
	       //System.out.println("opt:" + opt);
	       return x;
	   }
	    /****输入T0 10位UTC时间
	     ****输入32位密钥
	     ****Tc为60s
	     ****N为取6位
	     ****输出6位密码
	    *****/
	    public  int getCode(int T0,String K) 
	    {
	    	//1.T=T0/Tc
	    	int T,Tc=60,N=6;
	    	T=T0/Tc;
	    	byte [] t=intTobytes(T);
	    	byte [] k=str2bytes(K);
	    	//2.ID=(T|K)
	    	byte [] tmp =byteMerger(t,k);
	    	//3.S=F(ID)

			byte[] md = Sm3.hash(tmp);
	        /*for(int i=0;i<32;i++)
	        {
	        	System.out.printf("%d ",md[i]);
	        }
	        System.out.println();
	        int c=byte2Integer(md[0]);
	        System.out.println("c:" + c);*/
	        //4.OD==Truncate(S)
	        long OD=TruncateSM3(md);
			//5.P=oD%(10^N)
	        int p=(int)(OD%(long)(Math.pow(10,N)));
	    	return p;
	    }

	    public static String test(){
	    	return ""+new GoogleCodeJava().getCode(1286874120,"1f53de416ca79895086c64a7a2e5818a");
		}
      /*
  	  1286874060  4225e56988184643c10ba5c2f52b84e8 367650 67367650
  	  1286874120  1f53de416ca79895086c64a7a2e5818a 151017 64151017
  	  1286874180  047a3ee066bf6990e76bdfbdc31e2ff3 974147 15974147
  	  1286874240  a18e3552720d1dc7882615c3b0ee18cd 160563 13160563
  	  1286874300  18a5416f1a069ac9ac5ae5d754d889c7 857910 18857910
  	  1286874360  2f601262b57155de23c9aac638c6875f 548506 94548506
  	  1286874420  24166b29e2d10fefc7a2edeadaafa649 178480 79178480
  	  1286874480  b6cd6d9ee072f631f159d4508ae504a6 480547 34480547
  	  1286874540  38624a407322bad665b04a1139403cb0 133012 98133012
  	  1286874600  25971966bac6e7c0dedcf1082a6ed266 585088 73585088
  	  */

	    /*public static void main(String[] args) throws Exception {
	    	int T0 =1286874120;
	    	String K="1f53de416ca79895086c64a7a2e5818a";
    		TOTP t =new TOTP();
    		int pwd=t.getCode(T0,K);
	    	System.out.println("pwd:" + pwd);
	    	/*int i=0;
	    	while(true) {
	    	i++;
	    	int T0 = (int) (System.currentTimeMillis()/1000L);
    		String K="4225e56988184643c10ba5c2f52b84e8";
    		TOTP t =new TOTP();
    		int pwd=t.getCode(T0,K);
	    	System.out.println(i+" pwd:" + pwd);
	    	if(i>=60)
	    	{
	    		i=0;
	    	}
	    	Thread.sleep(1000);
	    	}*/
	     //}
}

