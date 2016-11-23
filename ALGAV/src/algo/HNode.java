package algo;

public class HNode {
    /*******************************************************/
    /*-------------------PARAMETERS------------------------*/
    /*******************************************************/
    private char c;
    private HNode left, mid, right;
    //ordre d'insertion
    //TODO changer par INTEGER
    private String val;

    /*******************************************************/
	/*-------------------GETTER/SETTER---------------------*/
    /*******************************************************/
    /**
     * @return the c
     */
    public char getC() {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(char c) {
        this.c = c;
    }

    /**
     * @return the left
     */
    public HNode getLeft() {
        return left;
    }

    /**
     * @param left the left to set
     */
    public void setLeft(HNode left) {
        this.left = left;
    }

    /**
     * @return the mid
     */
    public HNode getMid() {
        return mid;
    }

    /**
     * @param mid the mid to set
     */
    public void setMid(HNode mid) {
        this.mid = mid;
    }

    /**
     * @return the right
     */
    public HNode getRight() {
        return right;
    }

    /**
     * @param right the right to set
     */
    public void setRight(HNode right) {
        this.right = right;
    }

    /**
     * @return the val
     */
    public String getVal() {
        return val;
    }

    /**
     * @param val the val to set
     */
    public void setVal(String val) {
        this.val = val;
    }

}
