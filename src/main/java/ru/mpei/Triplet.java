package ru.mpei;

public class Triplet <E>{
    private Object[] values = new Object[5];
//    List<Integer> list = Arrays.asList(numbers);
    private Triplet<E> leftLink;
    private Triplet<E> rightLink;
    private int Size;

    public Triplet() {
    }

    public Triplet(Triplet<E> left, Triplet<E> right) {
        this.leftLink = left;
        this.rightLink = right;
    }
    public boolean checkFullTriplet(){ //метод для проверки заполненности массива, true если массив заполнен
        int county = 0;
        int i=0;
        while (i<5){
            if (values[i] == null){
                county ++;
            }
            i++;
        }
        if (county==0){
            return true;
        }else{
            return false; // если НЕ 0, то есть хотя бы 1 свободное место в массиве триплета
        }
    }
    public boolean checkOneEl(){ //метод для проверки наличия только 1 элемента в массиве в конце
        int county = 0;
        int i=0;
        while (i<5){
            if (values[i] != null){
                county ++;
            }
            i++;
        }
        if (county==1){
            return true; // возвращает true, если только 1 элемент в массиве
        }else{
            return false; // возвращает false, если в массиве больше 1 элемента
        }
    }
    public Object[] getValues() { //получаем доступ к массиву
        return values;
    }

    public Triplet<E> getLeftLink() {
        return leftLink;
    }

    public void setLeftLink(Triplet<E> leftLink) {
        this.leftLink = leftLink;
    }

    public Triplet<E> getRightLink() {
        return rightLink;
    }

    public void setRightLink(Triplet<E> rightLink) {
        this.rightLink = rightLink;
    }



}
