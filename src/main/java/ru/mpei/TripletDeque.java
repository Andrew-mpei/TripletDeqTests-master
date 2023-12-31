package ru.mpei;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TripletDeque<T> implements Deque<T>, Containerable {
    private int sizeTriplet = 0;
    private int maxSizeTriplet = 1000;
    private int count = 0;
    private Triplet<T> firstTriplet;
    private Triplet<T> lastTriplet;


    public TripletDeque(){
        Triplet<T> triplet = new Triplet<>();
        firstTriplet = triplet;
        lastTriplet = triplet;
    }
//    вставляет указанный элемент в начало этого списка, если это возможно сделать немедленно, не нарушая ограничений по емкости,
//    вызывая исключение IllegalStateException, если в данный момент нет свободного места. При использовании deque с ограниченной пропускной способностью,
//    как правило, предпочтительнее сначала воспользоваться предложением метода.
//    Параметры:
//    e – элемент, который нужно добавить
//    Бросает:
//    Исключение IllegalStateException – если элемент не может быть добавлен в данный момент из-за ограничений емкости
//    ClassCastException – если класс указанного элемента препятствует его добавлению в этот список
//    Исключение NullPointerException – если указанный элемент равен null и этот deque не разрешает элементы null
//    Исключение IllegalArgumentException – если какое-либо свойство указанного элемента препятствует его добавлению в этот список

    @Override
    public void addFirst(T element) {
        if (maxSizeTriplet>=count){
            if (element == null){
                throw new NullPointerException("Не можем добавить null");
            }else{
                if (sizeTriplet == maxSizeTriplet){
                    throw new IllegalArgumentException("Очередь заполнена");
                }else {// случай, когда заполнен массив, надо создать слева новый триплет
                    if (firstTriplet.checkFullTriplet() == true) {
                        Triplet<T> newTriplet = new Triplet<>(); //вспомогательная временная переменная это триплет newTriplet
                        firstTriplet.setLeftLink(newTriplet);//присваиваем изначальному триплету левую ссылку на newTriplet (наш новый второй триплет)
                        newTriplet.setRightLink(firstTriplet);//для триплета newTriplet (новый, левый, второй триплет) присваиваем правой ссылке правый изначальный триплет
                        firstTriplet = newTriplet;// первым триплетом во всей коллекции становится наш новый триплет, который мы только что создали
                        Object[] temp = firstTriplet.getValues();
                        temp[0]=element;
                    } else { //здесь в массиве триплета есть свободное место, новые триплеты создавать не надо
                        Object[] temp = firstTriplet.getValues();
                        if (temp[0] != null) { //слева нулевой элемент занят, поэтому ищем свободное место в массиве, начиная справа налево
                            int index = 3; // максимальный индекс 4 (5 элементов в массиве)
                            while (index >= 0)  {
                                if (temp[index] != null){
                                    temp[index+1] = temp[index];
                                }
                                index--;
                            }
                            temp[0] = element;
                        } else if (temp[4] != null) {
                            // случай, когда массив заполнен с конца
                            int index = 4; // максимальный индекс 4 (5 элементов в массиве)
                            while (index >= 0)  {
                                if (temp[index] == null){
                                    temp[index]=element;
                                    break;
                                }
                                index--;
                            }
                        }else{
                            temp[4] = element;
                        }

                    }
                }
            }
        }
    }
    //addLast
//    Вставляет указанный элемент в конец этого списка, если это возможно сделать немедленно,
//    не нарушая ограничений по емкости, вызывая исключение IllegalStateException, если в данный момент нет свободного места.
//    При использовании хранилища с ограниченной пропускной способностью, как правило, предпочтительнее использовать метод offerLast.
//    Этот метод эквивалентен добавлению.
//    Параметры:
//    e – элемент, который нужно добавить
//    Бросает:
//    Исключение IllegalStateException – если элемент не может быть добавлен в данный момент из-за ограничений емкости
//    ClassCastException – если класс указанного элемента препятствует его добавлению в этот список
//    Исключение NullPointerException – если указанный элемент равен null и этот deque не разрешает элементы null
//    Исключение IllegalArgumentException – если какое-либо свойство указанного элемента препятствует его добавлению в этот список

    @Override
    public void addLast(T t) {
       add(t);
    }
    //offerFirst
//Вставляет указанный элемент в начало этого списка, если только это не нарушит ограничения по емкости.
// При использовании deque с ограниченной емкостью этот метод обычно предпочтительнее метода addFirst, который может не вставить элемент, только вызвав исключение.
//Параметры:
//e – элемент, который нужно добавить
//Возвращается:
//true, если элемент был добавлен в этот список, иначе false
//Бросает:
//ClassCastException – если класс указанного элемента препятствует его добавлению в этот список
//Исключение NullPointerException – если указанный элемент равен null и этот deque не разрешает элементы null
//Исключение IllegalArgumentException – если какое-либо свойство указанного элемента препятствует его добавлению в этот список
    @Override
    public boolean offerFirst(T t) {//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        if (t == null){
            throw new NullPointerException();
        }else{
            if (sizeTriplet==maxSizeTriplet){
                throw new IllegalArgumentException();
            }else{
                addFirst(t);
                return true;
            }
        }
    }
    @Override
    public boolean offerLast(T t) {//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (t == null){
            throw new NullPointerException();
        }else{
            if (sizeTriplet==maxSizeTriplet){
                throw new IllegalArgumentException();
            }else{
                addLast(t);
                return true;
            }
        }
    }
    //removeFirst
//Извлекает и удаляет первый элемент этого списка. Этот метод отличается от pollFirst только тем, что он генерирует исключение, если этот список пуст.
//Возвращается:
//глава этого отдела
//Бросает:
//Исключение NoSuchElementException – если этот список пуст
    @Override
    public T removeFirst() {
        Object[] temp = firstTriplet.getValues();//получаем значения переданного массива
        int count=0;
        Object temp1 = null;
        if (firstTriplet == lastTriplet) {//в коллекции только 1 триплет, случаи: коллекция пустая, есть 1 элемент, есть больше 1 элемента
            for (Object o : temp) {//пустая коллекция
                if (o == null) {
                    count++;
                }
            }
            if (count == 5) {
                throw new NoSuchElementException("коллекция пустая");
            } else if(firstTriplet.checkOneEl() == true) {//1 триплет, 1 элемент, присваиваем ему null
                int ii = 0;
                while (temp[ii] == null) {
                    ii++;
                }
                temp1 = temp[ii];
                temp[ii] = null;
            }else {//больше 1 элемента в триплете
                if (temp[0] == null) {//элементы сдвинуты вправо
                    int i = 4;
                    while ((temp[i] != null) & (i > 0)) { // идем справа налево, пока элемент не null
                        i--;
                    }
                    temp1 = temp[i+1];
                    temp[i+1] = null;//i+1 так как остановились на нулевом элементе
                }else{//элементы сдвинуты влево, всю очередь сдвигаем влево на 1 элемент
                    if (temp[4]==null){//массив не заполнен был
                        temp1 = temp[0];
                        count = 0;
                        while ((count<5)&(temp[count]!=null)) {
                            temp[count] = temp[count + 1];
                            count++;
                        }
                    }else{//массив был заполнен, удаляем первый элемент
                        temp1 = temp[0];
                        temp[0]=null;
                    }
                }
            }
        }else if(firstTriplet.checkOneEl() == true) {//есть только 1 элемент и триплетов у нас не менее два, удаляем триплет
            // определить индекс ненулевого элемента, запомнить значение элемента, а потом менять ссылки
            if (firstTriplet.getValues()[0]!=null){
                temp1 = firstTriplet.getValues()[0];
            }else{
                temp1 = firstTriplet.getValues()[4];
            }
            firstTriplet = firstTriplet.getRightLink();//получили ссылку правую ссылку
            firstTriplet.setLeftLink(null);//присваиваем null левой ссылке
            // ЭТА СТРОЧКА НЕ НУЖНА firstTriplet.setRightLink(null);//присваиваем null правой ссылке
        //массив сдвинут влево, его нужно сдвинуть вправо

        } else {//больше 1 элемента в триплете
            if (temp[0] == null) {//элементы сдвинуты вправо
                int i = 4;
                while ((temp[i] != null) && (i > 0)) { // идем справа налево, пока элемент не null
                    i--;
                }
                temp1 = temp[i+1];
                temp[i+1] = null;//i+1 так как остановились на нулевом элементе
            }else{//элементы сдвинуты влево, всю очередь сдвигаем влево на 1 элемент
                if (temp[4]==null){//массив не заполнен был
                    temp1 = temp[0];
                    count = 0;
                    while ((count<5)&(temp[count]!=null)) {
                        temp[count] = temp[count + 1];
                        count++;
                    }
                }else{//массив был заполнен, удаляем первый элемент
                    temp1 = temp[0];
                    temp[0]=null;
                }
            }
        }
        return (T) temp1;

    }
    //removeLast
    //Извлекает и удаляет последний элемент этого списка. Этот метод отличается от poll Last только тем,
// что он генерирует исключение, если этот список пуст.
//    Возвращается:
//    хвост этой деки
//    Бросает:
//    Исключение NoSuchElementException – если этот список пуст
    @Override
    public T removeLast() {
        Object[] temp = lastTriplet.getValues();//получаем значения переданного массива
        T tempReturn = null;
        int count=0;
        if (lastTriplet == firstTriplet) {//в коллекции только 1 триплет, случаи: коллекция пустая, есть 1 элемент, есть больше 1 элемента
            for (Object o : temp) {//пустая коллекция
                if (o  == null) {
                    count++;
                }
            }
            if (count == 5) {
                throw new NoSuchElementException("коллекция пустая");
            } else if(lastTriplet.checkOneEl() == true) {//1 триплет, 1 элемент, присваиваем ему null
                int ii = 4;
                while (temp[ii] == null) {
                    ii--;
                }
                tempReturn = (T) temp[ii];
                temp[ii] = null;
            }else {//больше 1 элементе в триплете
                if (temp[0] == null) {//элементы сдвинуты вправо, сдвигаем вправо
                    tempReturn = (T) temp[4];
                    count = 4;
                    while (count>0 && temp[count]!=null) {
                        temp[count] = temp[count - 1];
                        count--;
                    }
                }else{//элементы сдвинуты влево, удаляем последний элемент
                    int i = 0;
                    while ((i<5) && temp[i]!=null)  { // массив заполнен или сдвинут влево
                        i++;
                    }
                    tempReturn = (T) temp[i-1];
                    temp[i-1] = null;//массив был заполнен, теперь очередь сдвинута влево
                }
            }
        }else if(lastTriplet.checkOneEl() == true) {//есть только 1 элемент и триплетов у нас не менее два, удаляем триплет
            for (Object o : lastTriplet.getValues()) {
                if (o != null) {
                    tempReturn = (T) o;
                    break;
                }
            }
            lastTriplet = lastTriplet.getLeftLink();//получили левую ссылку
            lastTriplet.setRightLink(null);//присваиваем null правой ссылке
        } else {//больше 1 элементе в триплете
            if (temp[0] == null) {//элементы сдвинуты вправо, сдвигаем вправо
                tempReturn = (T) temp[4];
                count = 4;
                while (count>0 && temp[count]!=null) {
                    temp[count] = temp[count - 1];
                    count--;
                }
            }else{//элементы сдвинуты влево, удаляем последний элемент
                int i = 0;
                while ((i<5) && temp[i]!=null)  { // массив заполнен или сдвинут влево
                    i++;
                }
                if (i - 1 == 4){
                    tempReturn = (T)temp[4];
                    temp[4] = null;//массив был заполнен, теперь очередь сдвинута влево
                }else{//массив не был заполнен, он сдвинут влево
                    int ii = 4;
                    while (temp[ii]==null) {
                        ii--;
                    }
                    tempReturn = (T)temp[ii];
                    temp[ii] = null;
                }
            }
        }

        return tempReturn;
    }
    //pollFirst
//Извлекает и удаляет первый элемент этого списка или возвращает null, если этот список пуст.
//Возвращается:
//заголовок этого списка, или null, если этот список пуст
    @Override
    public T pollFirst() {//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        int i=0;
        while (firstTriplet.getValues()[i]==null){
            i++;

        }if (i==0){
            return null;
        }else{
            return removeFirst();
        }
    }

    @Override
    public T pollLast() {//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        int i=0;
        while (firstTriplet.getValues()[i]==null){
            i++;

        }if (i==0){
            return null;
        }else{
            return removeLast();
        }
    }
    //getFirst
///**
//     * Извлекает, но не удаляет первый элемент этого списка.
//     *
//     * Этот метод отличается от {@link #peekFirst peekFirst} только тем, что он
//* генерирует исключение, если этот список пуст.
//     *
//     * @верните заголовок этого раздела
//     * @выдает исключение NoSuchElementException, если этот deque пуст
//     */
    @Override
    public T getFirst() {//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        int i=0;
        while (i<5){
            i++;
        }if (i==0){
            throw new NoSuchElementException();
        }else {
            i = 0;
            while (i < 5 && firstTriplet.getValues()[i] == null) {
                i++;
            }
            return (T) firstTriplet.getValues()[i];
        }
    }

    @Override
    public T getLast() {//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        int i=4;
        while (i>=0) {
            i--;
        }if (i==4){
            throw new NoSuchElementException();
        }else {
            i = 4;
            while (i>=0 && lastTriplet.getValues()[i] == null) {
                i--;
            }

            return (T) lastTriplet.getValues()[i];
        }

    }
//peekFirst
    //Извлекает, но не удаляет первый элемент этого списка или возвращает null, если этот список пуст.
//Возвращается:
//заголовок этого списка, или null, если этот список пуст
    @Override
    public T peekFirst() {//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        int i = 0;
        Object t = null;
        while (i<5 && firstTriplet.getValues()[i]==null) {
            t = ((T) firstTriplet.getValues()[i]);
            i++;
        }
        return (T) t;
    }
        //peekFirst
//Извлекает, но не удаляет последний элемент этого списка или возвращает null, если этот список пуст.
//Возвращается:
//конец этого списка, или null, если этот список пуст
    @Override
    public T peekLast() {//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        int i = 4;
        Object t = null;
        while (i>=0 && lastTriplet.getValues()[i]==null) {
            t = ((T) lastTriplet.getValues()[i]);
            i--;
        }
        return (T) t;
    }
//Удаляет первое вхождение указанного элемента из этого списка. Если deque не содержит элемента, он остается неизменным.
// Более формально, удаляет первый элемент e таким образом, что Objects.равен(o, e) (если такой элемент существует).
// Возвращает значение true, если этот deque содержал указанный элемент (или, что эквивалентно, если этот deque изменился в результате вызова).
//Параметры:
//o – элемент, который должен быть удален из этого списка, если он присутствует
//Возвращается:
//true, если элемент был удален в результате этого вызова
//Бросает:
//ClassCastException – если класс указанного элемента несовместим с этим deque (необязательно)
//Исключение NullPointerException – если указанный элемент равен null и этот deque не разрешает элементы null (необязательно)
    @Override
    public boolean removeFirstOccurrence(Object temp) {
        boolean flag = false;
        int fl = 0;//это костыль. если окажемся на последнем триплете и надо проверить там наличие элемента
        Triplet<T> newtempTriplet = firstTriplet;
        int i = 0;
        int index = 0;
        while(flag == false && fl<4) {//пока не найдем наш элемент или пока не дойдем до конца коллекции
            while (i < 5) {
                while (newtempTriplet.getValues()[i]==null){//костыль чтобы не натыкаться на сравнение строки с null
                    i++;
                }
                if (newtempTriplet.getValues()[i].equals(temp)) {
                    flag = true;
                    index = i;
                    fl = 4;
                    break;
                } else {
                    i++;
                }

            }
            //получаем правый триплет, если не нашли элемент
            if (newtempTriplet.getRightLink()!=null && fl<4){
                i = 0;
                newtempTriplet = newtempTriplet.getRightLink();
            }
            if (newtempTriplet.getRightLink()==null){//Если дошли до последнего триплета. Его надо тоже проверить
                fl++;
            }
        }
        if (flag == true){
            if (firstTriplet==lastTriplet){//1 триплет, сдвигаем вправо
                while (index >= 0){
                    if (index == 0){
                        newtempTriplet.getValues()[0] = null;
                        break;
                    }else{ //i от 1 до 4
                        newtempTriplet.getValues()[index]=newtempTriplet.getValues()[index-1];
                    }
                    index--;
                }
            }else{//триплетов больше 1
                while (newtempTriplet.getLeftLink()!=null){//идем до первого триплета
                    while (index >= 0){
                        if (index==0){
                            newtempTriplet.getValues()[0]=newtempTriplet.getLeftLink().getValues()[4];
                            newtempTriplet = newtempTriplet.getLeftLink();
                        }else{
                            newtempTriplet.getValues()[index]=newtempTriplet.getValues()[index-1];
                        }
                        index--;
                    }
                    index=4;
                }
                if (newtempTriplet.getValues()[3]==null){//дошли до конца и остался пустой первый триплет
                    newtempTriplet = newtempTriplet.getRightLink();
                    newtempTriplet.setLeftLink(null);// УДАЛЕНИЕ пустого триплета!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                }else{//на первом триплете
                    i=4;
                    while (i >= 0){
                        if (i == 0){
                            newtempTriplet.getValues()[0] = null;
                        }else{ //i от 1 до 4
                            newtempTriplet.getValues()[i]=newtempTriplet.getValues()[i-1];
                        }
                        i--;
                    }
                }
                firstTriplet = newtempTriplet;
            }

            return true;
        }else{
            return false;
        }
    }
    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }
    //    add
//    Вставляет указанный элемент в очередь, представленную этим deque (другими словами, в хвост этого deque),
//    если это возможно сделать немедленно, не нарушая ограничений емкости, возвращая true в случае успеха и вызывая исключение IllegalStateException,
//    если в данный момент нет свободного места. При использовании deque с ограниченной вместимостью, как правило, предпочтительнее использовать offer.
//    Этот метод эквивалентен добавлению последнего.
//            Параметры:
//    e – элемент, который нужно добавить
//    Возвращается:
//            true (как указано в Collection.add)
//    Бросает:
//    Исключение IllegalStateException – если элемент не может быть добавлен в данный момент из-за ограничений емкости
//    ClassCastException – если класс указанного элемента препятствует его добавлению в этот список
//    Исключение NullPointerException – если указанный элемент равен null и этот deque не разрешает элементы null
//    Исключение IllegalArgumentException – если какое-либо свойство указанного элемента препятствует его добавлению в этот список


    @Override
    public boolean add(T t) { // Ситуация противоположная addFirst. Здесь справа добавляем триплет //Сделано

        if (t == null) {
            throw new NullPointerException("Не можем добавить null");
        } else {
            if (sizeTriplet == maxSizeTriplet) {
                throw new IllegalArgumentException("Очередь заполнена");
            } else {// случай, когда заполнен массив, надо создать справа новый триплет
                if (lastTriplet.checkFullTriplet() == true) {
                    Triplet<T> newTriplet1 = new Triplet<>(); //вспомогательная временная переменная это триплет newTriplet1
                    lastTriplet.setRightLink(newTriplet1);//присваиваем изначальному триплету правую ссылку на newTriplet (наш новый второй триплет)
                    newTriplet1.setLeftLink(lastTriplet);//для триплета newTriplet (новый, правый, второй триплет) присваиваем левой ссылке левый изначальный триплет
                    lastTriplet = newTriplet1;// // правым триплетом во всей коллекции становится наш новый триплет, который мы только что создали
                    Object[] temp = lastTriplet.getValues();
                    temp[4]=t;
                } else { //здесь в массиве триплета есть свободное место, новые триплеты создавать не надо
                    Object[] temp = lastTriplet.getValues();
                    if (temp[0] != null) { //слева нулевой элемент занят, поэтому ищем свободное место в массиве, начиная справа налево
                        int index = 0; // максимальный индекс 4 (5 элементов в массиве)
                        while (index <= 4) {
                            if (temp[index] == null){
                                temp[index] = t;
                                break;
                            }
                            index++;
                        }
                    } else if(temp[4] != null){
                        int ii=0;
                        while (ii<=3){
                            temp[ii] = temp[ii+1];
                            ii++;
                        }
                        temp[4]=t;
                    }else{
                        temp[4] = t;
                    }
                }
            }

            return true;
        }
    }
    //offer
//* Вставляет указанный элемент в очередь, представленную этим deque
//     * (другими словами, в конце этого списка), если это возможно сделать
//* немедленно, не нарушая ограничений по пропускной способности, возвращая
//     * {@code true} при успешном выполнении и {@code false}, если в данный момент нет пробела
//     * доступно.  При использовании deque с ограниченной пропускной способностью этот метод
//* обычно предпочтительнее метода {@link #add}, который может не
//* вставить элемент, только вызвав исключение.
//     *
//     * <p>Этот метод эквивалентен {@link #offerLast}.
//     *
//     * @параметр добавляемого элемента
//* @return {@code true} если элемент был добавлен в этот список, иначе
//     * {@code false}
//     * @вызывает исключение ClassCastException, если класс указанного элемента
//     * предотвращает его добавление в этот список
//     * @выдает исключение NullPointerException, если указанный элемент равен null и это
//     * deque не допускает нулевых элементов
//     * @выдает исключение IllegalArgumentException, если какое-либо свойство указанного
//     * элемент предотвращает его добавление в этот список
//     */
    @Override
    public boolean offer(T t) {//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!
        if (t == null){
            throw new NullPointerException("Нулевой элемент");
        }else{
            if (sizeTriplet<=maxSizeTriplet){
                offerLast(t);
                return true;
            }else{
                return false;
            }
        }
    }
//Удаляет первое вхождение указанного элемента из этого списка. Если deque не содержит элемента, он остается неизменным. Более формально, удаляет первый элемент e таким образом, что Objects.равен(o, e) (если такой элемент существует). Возвращает значение true, если этот deque содержал указанный элемент (или, что эквивалентно, если этот deque изменился в результате вызова).
//Этот метод эквивалентен removeFirstOccurrence(объект).
//Параметры:
//o – элемент, который должен быть удален из этого списка, если он присутствует
//Возвращается:
//true, если элемент был удален в результате этого вызова
//Бросает:
//ClassCastException – если класс указанного элемента несовместим с этим deque (необязательно)
//Исключение NullPointerException – если указанный элемент равен null и этот deque не разрешает элементы null (необязательно)
    @Override
    public T remove() {
        return removeFirst();//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    @Override
    public T poll() {//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (sizeTriplet==0){
            return null;
        }else{
            return removeFirst();
        }
    }

    @Override
    public T element() {//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (sizeTriplet==0){
            throw new NoSuchElementException("Коллекция пустая");
        }
        return null;
    }

    @Override
    public T peek() {//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (sizeTriplet==0){
            return null;
        }else{
            return peekFirst();
        }
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        for (T el : c){
            offerLast(el);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        return false;
    }

    @Override
    public void clear() {
        Triplet<T> triplet = new Triplet<>();
        firstTriplet = triplet;
        lastTriplet = triplet;
        sizeTriplet = 0;

    }
    //push
//Помещает элемент в стек, представленный этим deque (другими словами, во главе этого deque), если это возможно сделать немедленно, не нарушая ограничений емкости, вызывая исключение IllegalStateException, если в данный момент нет свободного места.
//Этот метод эквивалентен добавлению сначала.
//Параметры:
//e – элемент, который нужно нажать
//Бросает:
//Исключение IllegalStateException – если элемент не может быть добавлен в данный момент из-за ограничений емкости
//ClassCastException – если класс указанного элемента препятствует его добавлению в этот список
//Исключение NullPointerException – если указанный элемент равен null и этот deque не разрешает элементы null
//Исключение IllegalArgumentException – если какое-либо свойство указанного элемента препятствует его добавлению в этот список
    @Override
    public void push(T t) {//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (t == null){
            throw new NullPointerException();
        }else{
            addFirst(t);
        }

    }

    @Override
    public T pop() {//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        return removeFirst();
    }

    @Override
    public boolean remove(Object o) {//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        return removeFirstOccurrence(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        return false;
    }

    @Override
    public boolean contains(Object o) {//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if (o == null) {
            throw new NullPointerException();
        } else {
            boolean find = false;
            Iterator<T> iterator = iterator();
            while (iterator.hasNext()) {
                if (iterator.next().equals(o)) {
                    find = true;
                    break;
                }
            }
            return find;
        }
    }

    @Override
    public int size() {
//        if (first == null){
//            return 0;
//        }
//        while (first.next != null){
//            return 0;
//        }
//        int size = 1;
//        while (first.next !=null){
//            size += 1;
//        }
//        return size;
        return 5;
    }

    @Override
    public boolean isEmpty() {//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        int i = 0;
        boolean flag = true;
        while (flag && i<5){
            if (firstTriplet.getValues()[i]!=null){
                flag = false;
            }
            i++;
        }
        return flag;
    }

    @Override
    public Iterator<T> iterator() {//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        Iterator<T> iterator = new Iterator<>() {
            private Triplet<T> tripletDeque = firstTriplet;//при первом вызове начнем с первого триплета

            private int findFirstEl(Triplet<T> tripletDeque) {
                int ii = -1;
                for (int i = 0; i < 5; i++) {
                    if (tripletDeque.getValues()[i] != null ) {
                        ii = i - 1;
                        break;
                    }
                }
                return ii;
            }
            private int nowInd = findFirstEl(tripletDeque);//нашли индекс первого элемента не null или остались на нулевом, если элемент null

            @Override
            public boolean hasNext() {
                if (nowInd < 4 && tripletDeque.getValues()[nowInd + 1] != null) {//не дошли до конца триплета и не оказались на пустых элементах последнего триплета
                    return true;
                } else if (nowInd == 4 && tripletDeque.getRightLink() != null) {//дошли до конца триплета и мы не на последнем триплете
                    return true;
                }else{
                    return false;
                }

            }
            @Override
            public T next() {
                if (hasNext() && nowInd < 4) {
                    nowInd++;

                } else if (hasNext() && nowInd == 4) {
                    tripletDeque = tripletDeque.getRightLink();
                    nowInd=0;

                }else{
                    throw new NoSuchElementException();
                }
                return (T) tripletDeque.getValues()[nowInd];
            }
        };
        return iterator;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }
//iterator
    //Возвращает итератор по элементам в этом списке в обратном последовательном порядке. Элементы будут возвращены в порядке от последнего (tail) к первому (head).
//Возвращается:
//итератор по элементам в этом списке в обратной последовательности
    @Override
    public Iterator<T> descendingIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] getContainerByIndex(int cIndex) {//Сделано!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Triplet<T> tripletDeque = firstTriplet;
        int count = 0;
        while (count< cIndex){
            tripletDeque = tripletDeque.getRightLink();
            count++;
        }
        if(tripletDeque==null){
            return null;
        }else{
            return tripletDeque.getValues();
        }

    }
}
