package fyhThesis.bean;

import Jama.Matrix;

public class DistanceMethod {
	public static class OuShiDistance extends AbstractDistance
	{

		protected double p=2.0;
		
		public double calacDistance(int ui, int vi)
		{
			// TODO Auto-generated method stub
			double sum=0;
			
			for(int i=0;i<dataList[0].length;i++)
			{
				sum+=Math.pow(Math.abs(dataList[ui][i]-dataList[vi][i]),p);
			}
			
			return Math.pow(sum, 1/p);
		}

		@Override
		public void preCalac() {
			// TODO Auto-generated method stub
			this.distanceList=new double[dataList.length*(dataList.length-1)/2];
		}

		@Override
		public double calacAllDistance() {
			// TODO Auto-generated method stub
			int k=0;
			
			double sum=0;
			for(int i=0;i<dataList.length;i++)
			{
				for(int j=i+1;j<dataList.length;j++)
				{
					distanceList[k]=calacDistance(i,j);
					sum+=distanceList[k];
					k++;
				}
			}
			this.validDistanceCount=k;
			
			return  sum/k;
		}
	}
	
	public static class InnerDistance extends AbstractDistance
	{

		
		protected double[] innerList;
		protected double T;
		
		public double calacInner(int ui,int vi)
		{
			double sum=0;
			for(int i=0;i<dataList[0].length;i++)
			{
				double t=dataList[ui][i]*dataList[vi][i];
				
				sum+=t;
			}
			
			return sum;
		}
		
		
		@Override
		public void  preCalac(){
			// TODO Auto-generated method stub
			
			this.distanceList=new double[dataList.length*(dataList.length-1)/2];
			this.innerList=new double[distanceList.length];
			
			double T=Double.MIN_VALUE;
			
			int k=0;
			for(int i=0;i<dataList.length;i++)
			{
				for(int j=i+1;j<dataList.length;j++)
				{
					innerList[k]=calacInner(i,j);
					if(T<innerList[k])
						T=innerList[k];
					System.out.println("pre-"+k);
					k++;
					
					
				}
			}
		
		}
		
	

		@Override
		public double calacAllDistance() {
			// TODO Auto-generated method stub
			double sum=0;
			for(int i=0;i<innerList.length;i++)
			{
				distanceList[i]=T-innerList[i];
				sum+=distanceList[i];
				
				System.out.println("dis-"+i);
			}
			this.validDistanceCount=innerList.length;
			return sum/innerList.length;
		}

		
	}
	
	
	public static class CosDistance extends AbstractDistance
	{

	
		public double calacDistance(int ui, int vi) {
			// TODO Auto-generated method stub
			
			double sum=0;
			double uSum=0,vSum=0;
			
			for(int i=0;i<dataList[0].length;i++)
			{
				double t=dataList[ui][i]*dataList[vi][i];
				
				sum+=t;
				
				uSum+=dataList[ui][i]*dataList[ui][i];
				vSum+=dataList[vi][i]*dataList[vi][i];
			}
			
			return (1-sum/(Math.sqrt(uSum)*Math.sqrt(vSum)));
		}

		@Override
		public double calacAllDistance() {
			// TODO Auto-generated method stub
			int k=0;
			
			double sum=0;
			for(int i=0;i<dataList.length;i++)
			{
				for(int j=i+1;j<dataList.length;j++)
				{
					distanceList[k]=calacDistance(i,j);
					sum+=distanceList[k];
					k++;
				}
			}
			this.validDistanceCount=k;
			return  sum/k;
		}

		@Override
		public void preCalac() {
			// TODO Auto-generated method stub
			this.distanceList=new double[dataList.length*(dataList.length-1)/2];
		}
	}
	
	public static class SLDistance extends AbstractDistance
	{
		protected double [] avg,sigma;
		protected double p=3.0;
		public double calacDistance(int ui,int vi)
		{
			double sum=0;
			
			for(int i=0;i<dataList[0].length;i++)
			{
				sum+=Math.pow(Math.abs((dataList[ui][i]-dataList[vi][i])/sigma[i]),p);
			}
			
			double rst= Math.pow(sum, 1/p);
			//System.out.println("("+ui+","+vi+")--"+rst);
			return rst;
		}
		
		@Override
		public double calacAllDistance() {
			// TODO Auto-generated method stub
			int k=0;
			
			double sum=0;
			for(int i=0;i<dataList.length;i++)
			{
				for(int j=i+1;j<dataList.length;j++)
				{
					distanceList[k]=calacDistance(i,j);
					sum+=distanceList[k];
					k++;
				}
			}
			this.validDistanceCount=k;
			return  sum/k;
		}

		@Override
		
		public void preCalac() {
			// TODO Auto-generated method stub
			this.distanceList=new double[dataList.length*(dataList.length-1)/2];
			this.avg=new double[dataList[0].length];
			this.sigma=new double[dataList[0].length];
			
			for(int j=0;j<dataList[0].length;j++)
			{
				this.avg[j]=0;
				for(int i=0;i<dataList.length;i++)
				{
					this.avg[j]+=dataList[i][j];
				}
				this.avg[j]/=dataList.length;
				//System.out.println("avg"+j+"--"+this.avg[j]);
			}
			
			
			for(int j=0;j<dataList[0].length;j++)
			{
				this.sigma[j]=0;
				for(int i=0;i<dataList.length;i++)
				{
					double t=(dataList[i][j]-this.avg[j]);
					t*=t;
					this.sigma[j]+=t;
				}
				
				this.sigma[j]=Math.sqrt(this.sigma[j]/dataList.length);
				//System.out.println("sigma"+j+"--"+this.sigma[j]);
			}
			
		}
		
		
	}
	
	public static class CorrDistance extends AbstractDistance
	{

	
		public double calacDistance(int ui, int vi) {
			// TODO Auto-generated method stub
			
			double sum=0;
			double uSum=0,vSum=0;
			double avgU=0,avgV=0;
			
			for(int i=0;i<dataList[0].length;i++)
			{
				avgU+=dataList[ui][i];
				avgV+=dataList[vi][i];
			}
			
			avgU/=dataList[0].length;
			avgV/=dataList[0].length;
			
			
			for(int i=0;i<dataList[0].length;i++)
			{
				double t1,t2;
				t1=dataList[ui][i]-avgU;
				t2=dataList[vi][i]-avgV;
				sum+=t1*t2;
				
				uSum+=t1*t1;
				vSum+=t2*t2;
			}
			
			double rst=(1-sum/(Math.sqrt(uSum)*Math.sqrt(vSum)));
			
			//System.out.println("("+ui+","+vi+")--"+rst);
			return  rst;
		}

		@Override
		public double calacAllDistance() {
			// TODO Auto-generated method stub
			int k=0;
			
			double sum=0;
			for(int i=0;i<dataList.length;i++)
			{
				for(int j=i+1;j<dataList.length;j++)
				{
					distanceList[k]=calacDistance(i,j);
					sum+=distanceList[k];
					k++;
				}
			}
			this.validDistanceCount=k;
			return  sum/k;
		}

		@Override
		public void preCalac() {
			// TODO Auto-generated method stub
			this.distanceList=new double[dataList.length*(dataList.length-1)/2];
		}
	}
	
	
	public static class MahaDistance extends AbstractDistance
	{

		
		protected double [] avg,sigma;
		Matrix covMatrix;
		
		public double calacDistance(int ui, int vi)
		{
			// TODO Auto-generated method stub
			double [][]uv=new double[1][dataList[0].length];


			for(int i=0;i<dataList[0].length;i++)
			{
				uv[0][i]=dataList[ui][i]-dataList[vi][i];
			}
			
			Matrix uvMatrix=new Matrix(uv);
			
			return Math.sqrt(uvMatrix.times(covMatrix).times(uvMatrix.transpose()).get(0, 0));
			
			
			
		
		}

		@Override
		public void preCalac() {
			// TODO Auto-generated method stub
		
			
			
			
			this.distanceList=new double[dataList.length*(dataList.length-1)/2];
			this.avg=new double[dataList[0].length];
			this.sigma=new double[dataList[0].length];
			
			double [][]cov=new double[dataList[0].length][dataList[0].length];
			
			
			for(int j=0;j<dataList[0].length;j++)
			{
				this.avg[j]=0;
				for(int i=0;i<dataList.length;i++)
				{
					this.avg[j]+=dataList[i][j];
				}
				this.avg[j]/=dataList[0].length;
			}
			
			/*
			for(int j=0;j<dataList[0].length;j++)
			{
				this.sigma[j]=0;
				for(int i=0;i<dataList.length;i++)
				{
					double t=(dataList[i][j]-this.avg[j]);
					t*=t;
					this.sigma[j]+=t;
				}
				
				this.sigma[j]=Math.sqrt(this.sigma[j]/dataList[0].length);
			}
			*/
			
			for(int i=0;i<cov.length;i++)
			{
				
				for(int j=0;j<cov.length;j++)
				{
					cov[i][j]=0;
					
					for(int k=0;k<dataList.length;k++)
					{
						cov[i][j]+=(dataList[k][i]-avg[i])*(dataList[k][j]-avg[j]);
					}
					cov[i][j]/=dataList.length;
					
				}
			}
			
			covMatrix=new Matrix(cov);
			covMatrix=covMatrix.inverse();
		
		}

		@Override
		public double calacAllDistance() {
			// TODO Auto-generated method stub
			int k=0;
			
			double sum=0;
			for(int i=0;i<dataList.length;i++)
			{
				for(int j=i+1;j<dataList.length;j++)
				{
					distanceList[k]=calacDistance(i,j);
					sum+=distanceList[k];
					k++;
				}
			}
			this.validDistanceCount=k;
			return  sum/k;
		}
	}
	
	public static abstract class AbstractDigitDistance extends AbstractDistance
	{

		protected int c=2;
		
		@Override
		public void preCalac() {
			// TODO Auto-generated method stub
			this.distanceList=new double[dataList.length*(dataList.length-1)/2];
			
			double unit=2.0/c;
			for(int i=0;i<dataList.length;i++)
			{
				for(int j=0;j<dataList[0].length;j++)
				{
					dataList[i][j]=(int)( (dataList[i][j]+1)/unit );
				}
			}
		}
		
	}
	
	public static class HDistance extends AbstractDigitDistance
	{

		
		public double calacDistance(int ui,int vi)
		{
			int sum=0;
			for(int i=0;i<dataList[0].length;i++)
			{
				if(dataList[ui][i]!=dataList[vi][i])
					sum++;
			}
			
			System.out.println("("+ui+","+vi+")--"+sum);
			return sum;
		}
		
		@Override
		public double calacAllDistance() {
			// TODO Auto-generated method stub
			int k=0;
			
			double sum=0;
			for(int i=0;i<dataList.length;i++)
			{
				for(int j=i+1;j<dataList.length;j++)
				{
					distanceList[k]=calacDistance(i,j);
					sum+=distanceList[k];
					k++;
				}
			}
			this.validDistanceCount=k;
			return  sum/k;
		}
		
		@Override
		public void preCalac() {
			// TODO Auto-generated method stub
			this.c=2;
			super.preCalac();
		}
		
	}
	
	public static class H2Distance extends AbstractDigitDistance
	{

		
		public double calacDistance(int ui,int vi)
		{
			int sum=0;
			for(int i=0;i<dataList[0].length;i++)
			{
				sum+=Math.abs(dataList[ui][i]-dataList[vi][i]);
			}
			System.out.println("("+ui+","+vi+")--"+sum);
			return sum;
		}
		
		@Override
		public double calacAllDistance() {
			// TODO Auto-generated method stub
			int k=0;
			
			double sum=0;
			for(int i=0;i<dataList.length;i++)
			{
				for(int j=i+1;j<dataList.length;j++)
				{
					distanceList[k]=calacDistance(i,j);
					sum+=distanceList[k];
					k++;
				}
			}
			this.validDistanceCount=k;
			return  sum/k;
		}
	}
	
	public static class JDistance extends AbstractDigitDistance
	{

	
		double nanFreq=0;
		
		public double calacDistance(int ui,int vi)
		{
			double m01=0,m10=0,m11=0;
			for(int i=0;i<dataList[0].length;i++)
			{
				if( dataList[ui][i]==0 &&dataList[vi][i]==1)
					m01++;
				else if( dataList[ui][i]==1 &&dataList[vi][i]==0)
					m10++;
				else if( dataList[ui][i]==1 &&dataList[vi][i]==1)
					m11++;
			}
			double rst=1-m11/(m01+m10+m11);
		
			
			//System.out.println("("+ui+","+vi+")--"+rst);
			return rst;
		}
		
		@Override
		public double calacAllDistance() {
			// TODO Auto-generated method stub
			int k=0;
			nanFreq=0;
			double sum=0;
			for(int i=0;i<dataList.length;i++)
			{
				for(int j=i+1;j<dataList.length;j++)
				{
					double t=calacDistance(i,j);
					if(! Double.isNaN(t))
					{
						distanceList[k]=calacDistance(i,j);
						sum+=distanceList[k];
						k++;
					}
					else
					{
						this.nanFreq++;
					}
				}
			}
			this.validDistanceCount=k;
			System.out.println("nanFreq"+nanFreq);
			return sum/k;
		}
		
		@Override
		public void preCalac() {
			// TODO Auto-generated method stub
			this.c=2;
			super.preCalac();
		}
	}
	
	
	public static class JMDistance extends AbstractDigitDistance
	{
		

		boolean [] invalidRow;
		protected double n=4;
		double nanFreq=0;
		boolean [][] invalidValue;
		double [][] freqValue;
		public double calacDistance(int ui,int vi)
		{
			double sum1=0,sum2=0,sum3=0;
			for(int i=0;i<dataList[0].length;i++)
			{
				if( invalidValue[i][(int) dataList[ui][i]]==false && invalidValue[i][(int) dataList[vi][i]]==false)
				{
					sum1+=(1-1/(Math.abs(dataList[ui][i]-dataList[vi][i])+1) );
				}
				else if( invalidValue[i][(int) dataList[ui][i]]==false && invalidValue[i][(int) dataList[vi][i]]==true)
				{
					sum2+=(1-1/(Math.abs(dataList[ui][i]-dataList[vi][i])+1) );
				}
				else if( invalidValue[i][(int) dataList[ui][i]]==true && invalidValue[i][(int) dataList[vi][i]]==false)
				{
					sum3+=(1-1/(Math.abs(dataList[ui][i]-dataList[vi][i])+1) );
				}
			}
			
			double rst=1-sum1/(sum1+sum2+sum3);
			if(Double.isNaN(rst))
				System.out.println("("+ui+","+vi+")--"+rst);
			return rst;
		}
		
		@Override
		public double calacAllDistance() {
			// TODO Auto-generated method stub
			int k=0;
			validDistanceCount=0;
			nanFreq=0;
			double sum=0;
			for(int i=0;i<dataList.length;i++)
			{
				for(int j=i+1;j<dataList.length;j++)
				{
					if(invalidRow[i]==false && invalidRow[j]==false)
					{
						double t=calacDistance(i,j);
						
						if(! Double.isNaN(t))
						{
							distanceList[k]=t;
							sum+=distanceList[k];
							validDistanceCount++;
							k++;
						}
						else
						{
							invalidRow[j]=true;
							nanFreq++;
						}
					}
				}
			}
			
			System.out.println("nan count"+nanFreq);
			
			return  sum/validDistanceCount;
		}
		
		@Override
		public double fangcha(double avg)
		{
			double sum=0;
			for(int i=0;i<validDistanceCount;i++)
			{
				double t=distanceList[i]-avg;
				t*=t;
				
				sum+=t;
			}
			
			return sum/validDistanceCount;
		}
		
		public interface IinvalidMethod
		{
			public void judge();
		}
		
		public IinvalidMethod invalidMethodImpl=new IinvalidMethod()
		{

			@Override
			public void judge() {
				// TODO Auto-generated method stub
				
				
				int [][]sortedCid=new int [dataList[0].length][c];
				
				for(int i=0;i<sortedCid.length;i++)
				{
					for(int j=0;j<c;j++)
						sortedCid[i][j]=j;
				}
				
				
				
				int t;
				for(int k=0;k<sortedCid.length;k++)
				{
					for(int i=0;i<c-1;i++)
					{
						for(int j=0;j<c-1-i;j++)
						{
							if(freqValue[k][sortedCid[k][j]]<freqValue[k][sortedCid[k][j+1]])
							{
								t=sortedCid[k][j];
								sortedCid[k][j]=sortedCid[k][j+1];
								sortedCid[k][j+1]=t;
							}
						}
					}
				}
				
				for(int k=0;k<sortedCid.length;k++)
				{
					for(int j=0;j<c;j++)
					{
						if(j<n)
						{
							invalidValue[k][sortedCid[k][j]]=true;
						}
						else
						{
							invalidValue[k][sortedCid[k][j]]=false;
						}
							
					}
				}
			}
			
		};
		
		@Override
		public void preCalac() {
			// TODO Auto-generated method stub
			this.c=10;
			super.preCalac();
			invalidRow=new boolean[dataList.length];
			invalidValue=new boolean[dataList[0].length][c];
			freqValue=new double[dataList[0].length][c];
			
			for(int j=0;j<dataList[0].length;j++)
			{
				for(int i=0;i<dataList.length;i++)
				{
					freqValue[j][(int)dataList[i][j]]++;
				}
				
				for(int k=0;k<c;k++)
				{
					freqValue[j][k]/=dataList.length;
				}
				
			}
			
			
			invalidMethodImpl.judge();
			System.out.println();
		}
	}
}
