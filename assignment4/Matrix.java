import java.util.*;

public class Matrix {
	public int rs;
	public int cs;
	
	ArrayList<NonZero> list;
	
	public Matrix() {
		rs=cs=0;
		list=new ArrayList<>();
	}
	
    public Matrix(int rs,int cs){
        this.rs=rs;
        this.cs=cs;
		list=new ArrayList<>();
    }

    public static Matrix add(Matrix m1, Matrix m2){
        Matrix m=new Matrix();
        m.rs=m1.rs;
        m.cs=m1.cs; 
        ArrayList<NonZero> list1=m1.list;
        ArrayList<NonZero> list2=m2.list;
        int i=0;int j=0;
        NonZero nz1=new NonZero();
        NonZero nz2=new NonZero();
        while (i<list1.size()&&j<list2.size()){
            nz1=list1.get(i);
            nz2=list2.get(j);
            if (nz1.r==nz2.r&&nz1.c==nz2.c){
                if (nz1.value+nz2.value==0){
                    i++;j++;
                    continue;
                }else{
                    NonZero nz=new NonZero();
                    nz.r=nz1.r;
                    nz.c=nz1.c;
                    nz.value=nz1.value+nz2.value;
                    m.list.add(nz);
                    i++;j++;continue;
                }
            } else if (nz1.r==nz2.r&&nz1.c<nz2.c){
                m.list.add(nz1);
                i++;continue;
            } else if (nz1.r==nz2.r&&nz1.c>nz2.c){
                m.list.add(nz2);
                j++;continue;
            } else if (nz1.r<nz2.r){
                m.list.add(nz1);
                i++;continue;
            } else if (nz1.r>nz2.r){
                m.list.add(nz2);
                j++;continue;
            }
        }
        if (i>=list1.size()){
            while (j<list2.size()){
                m.list.add(list2.get(j));
                j++;
            }
        } else{
            while (i<list1.size()){
                m.list.add(list1.get(i));
                i++;
            }
        }
        return m;
    }
}
