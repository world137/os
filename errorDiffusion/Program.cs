using System;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Threading;


public class ErrorDiffusion{

    public static int N = 2 ;
    public static int H,W;
    public static int[,] Input;
    public static int[,] Output;
    public static bool[,] Done;


    public static void ProcessRow(object x){
        int h =(int)x;
        while (h<=H)
        {
            for(int w=1;w<=W;w++){
                while(!Done[h-1,w+1]){
                    //wait(spinlock)
                }
                Output[h,w] = Input[h,w] < 128 ? 0 : 1;
                int err = Input[h,w] - (255 * Output[h,w]);

                Input[h,w+1] += (err*7)/16;
                Input[h+1,w-1] += (err*3)/16;
                Input[h+1,w] += (err*5)/16;
                Input[h+1,w+1] += (err*1)/16;
                Input[h+1,w+1] += (err*1)/16;

                Done[h,w] = true;
                }
                h = h+N;
        }
    }
    public static void Main(){
        Bitmap bmp = new Bitmap("error2.jpg");
        H = bmp.Height;
        W = bmp.Width;

        Input = new int[H+2,W+2]; //default value = 0
        Output = new int[H+2,W+2]; //default value = 0
        Done = new bool[H+2,W+2]; //default value = false

        for(int w=0; w < W+2; w++){
            Done[0,w] = true;
            Done[H+1,w] = true;
        }

        for(int h=0; h < H+2; h++){
            Done[h,0] = true;
            Done[h,W+1] = true;
        }
        

        for(int h=1; h <= H; h++){
            for(int w=1; w <= W; w++){
                Color px = bmp.GetPixel(w-1,h-1);
                Input[h,w] = px.R;
            }
        }


        Thread[] t = new Thread[N];
        for(int i=0; i < N; i++){
            t[i]=new Thread(ProcessRow);
        }

        long time1 = DateTime.Now.Millisecond;


        for(int i=0; i<N; i++){
            t[i].Start(i+1);
        }
        for(int i=0; i<N; i++){
            t[i].Join();
        }

        long time2 = DateTime.Now.Millisecond;
        Console.Write(N);
        Console.WriteLine(" Thread");

        Console.Write("Exe Time : ");
        Console.WriteLine(time2-time1);

        for(int h=1; h<=H; h++){
            for(int w=1; w<=W; w++){
                if(Output[h,w] == 0){bmp.SetPixel(w-1,h-1,Color.Black);}
                else if(Output[h,w] == 1){bmp.SetPixel(w-1,h-1,Color.White);}
                else{
                    throw new Exception("Error");
                }
            }
        }
        bmp.Save("output3.png",ImageFormat.Png); 
    }
    
}