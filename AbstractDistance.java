package fyhThesis.bean;

public abstract class AbstractDistance {
	
	protected double validDistanceCount=0;
	protected double [][] dataList;
	
	protected double [] distanceList;
	
	
	public double[][] getDataList() {
		return dataList;
	}

	public void setDataList(double[][] dataList) {
		this.dataList = dataList;
	}

	public double[] getDistanceList() {
		return distanceList;
	}

	public void setDistanceList(double[] distanceList) {
		this.distanceList = distanceList;
	}

	public abstract double calacAllDistance();
	
	public abstract void preCalac();
	
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
	
}
