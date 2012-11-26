package net.floodlightcontroller.statCollector;


public class MnHost 
{
	public String src;
	int pktNum;
	
	/**
     * Get Source IP
     * @return
     */
    public String getSrc() {
        return src;
    }

    /**
     * Set Source IP
     * @param source
     */
    public MnHost setSrc(String source) {
        src = source;
        return this;
    }
    
    public int getPktNum(){
    	return this.pktNum;
    }
	
    public MnHost setPktNum(int pNum){
    	pktNum = pNum;
    	return this;
    }

}
