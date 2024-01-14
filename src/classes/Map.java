package classes;

import classes.KeyValue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Map<K, V> implements Iterable<KeyValue<K, V>> {

    private static final int INITIAL_CAPACITY = 16; // размер таблицы по умолчании
    private static final double LOAD_FACTOR = 0.80d; // коэфф загрузки - фактор заполнения
    private LinkedList<KeyValue<K, V>>[] slots; // сама таблица
    private int count = 0; // кол-во занятых слотов
//    capacity - текущее кол-во слотов
    private int collisionCount = 0;

    public int getCollisionCount() {
        return this.collisionCount;
    }
    public double getLoadFactor(){
        return (double) (count+1) /capacity();
    }


    public Map() {
        this(INITIAL_CAPACITY);
    }

    public Map(int capacity) {
        slots = (LinkedList<KeyValue<K, V>>[]) new LinkedList<?>[capacity];
        count = 0;
    }

    public void add(K key, V value) {
//        System.out.println(addOrReplace(key,value) ? "add new element" : "replace old element");
        addOrReplace(key, value);
    }

    public int findSlotNumber(K key) { // делит хеш на длину массива, чтобы тот поместился и не выпал за пределы.
        return Math.abs(key.hashCode()) % this.slots.length;
    }

    private void growIfNeeded() {
        if (getLoadFactor() > LOAD_FACTOR) {
            System.out.println("grow table");
            this.grow();
        }
    }

    private void grow() {
        int newCapacity = this.slots.length * 2;
        LinkedList<KeyValue<K, V>>[] newSlots = (LinkedList<KeyValue<K, V>>[]) new LinkedList<?>[newCapacity];

        for (LinkedList<KeyValue<K, V>> slot : this.slots) {
            if (slot != null) {
                for (KeyValue<K, V> keyValue : slot) {
                    int newSlotNumber = findSlotNumber(keyValue.getKey());
                    if (newSlots[newSlotNumber] == null) {
                        newSlots[newSlotNumber] = new LinkedList<>();
                    }
                    newSlots[newSlotNumber].add(keyValue);
                }
            }
        }

        this.slots = newSlots;
    }

    public int size() {
        return this.count;
    }

    public int capacity() {
        return this.slots.length;
    }

    public boolean addOrReplace(K key, V value) {
        if (key == null || value == null) { // проверка на null
            throw new NullPointerException();
        }

        growIfNeeded(); // вызываем проверку на коэфф.загрузки - 80%

        int slotNumber = findSlotNumber(key); // выбираем индекс ячейки, в которую будем вставлять
        LinkedList<KeyValue<K, V>> slot = slots[slotNumber]; // берём list из этого слота

        if (slot == null) { // если этот list пустой - инициализируем его и вставляем в массив
            slot = new LinkedList<>();
            slots[slotNumber] = slot;
            count++; // очередная занятая ячейка
        }
        else { // если в слоте уже есть list - у нас коллизия
//            System.out.println("we get collision on slot " + slotNumber);
            collisionCount++;
        }
//        идём по нашему list и проверяем каждое значение, если такой элемент есть - заменяем
        for (KeyValue<K,V> element : slots[slotNumber]){ // замена элемента если такой есть
            if (element.getKey().equals(key)){
                element.setValue(value);
                return false; // прерываем выполнение, если замена выполнена
            }
        }
//        если такого элемента нет - вставляем его в начало списка (как в лекции)
        slots[slotNumber].addFirst(new KeyValue<>(key,value));
        return true;
    }

    public V get(K key) {
        return find(key).getValue(); // ищем ключ в list и возвращаем его значение
    }

    public KeyValue<K, V> find(K key) {
        if (key == null) { // проверка на Null
            throw new NullPointerException();
        }
        int slotNumber = findSlotNumber(key); // достаём индекс в котором лежит этот ключ
        LinkedList<KeyValue<K, V>> slot = slots[slotNumber]; // достаём list по данному индексу
        if (slot == null) {
            return null;
        }
        if (slots[slotNumber] != null) {
            for (KeyValue<K, V> element : slots[slotNumber]) { // проходим по всему list
                if (element.getKey().equals(key)) { // если это нужный ключ
                    return element; // возвращаем этот элемент
                }
            }
        }
        return null;
    }

    public boolean containsKey(K key) { // возвращает true/false в зависимости от того найден ли элемент по такому ключу
        return find(key) != null; // если нашли ключ - true
    }

    public boolean remove(K key) { // возвращает true/false в зависимости от того найден ли элемент для удаления и соответственно удалён ли он
        if (key == null) { // проверка на Null
            throw new NullPointerException();
        }
        int slotNumber = findSlotNumber(key); // достаём индекс в котором лежит этот ключ
        LinkedList<KeyValue<K, V>> slot = slots[slotNumber]; // достаём list по данному индексу
        if (slot == null) {
            return false;
        }
        KeyValue<K, V> existing = find(key); // ищем элемент для удаления
        if (existing == null) {
            return false; // элемент не найден
        }
        slot.remove(existing); // элемент найден и удалён
        count--;
        return true;
    }
    public void clear() {
        Arrays.setAll(slots, i -> slots[i]=null);
        count=0;
        /*Arrays.fill(slots, null);
        this.slots= (LinkedList<classes.KeyValue<K, V>>[]) Arrays.stream(this.slots).map(e->null).toArray();*/
    }

    public void printHashTableStats() {
        System.out.println("Сейчас таблица заполнена на " +
                getLoadFactor() + " процентов.");
        System.out.println("Количество коллизий: " + getCollisionCount());
        System.out.println("Текущее количество заполненных ячеек: " + size());
        System.out.println("Текущий размер таблицы: " + capacity());
    }

    /*
       функция keys() проходит через все элементы массива slots,
       извлекает ключи из каждого связанного списка и добавляет их в новый связанный список keys,
       который затем возвращается как результат.
    */
    public Iterable<K> keys() { // возвращает итерируемый объект, содержащий все ключи
        LinkedList<K> keys = new LinkedList<>();
        for (LinkedList<KeyValue<K, V>> list : slots) {
            if (list != null) {
                for (KeyValue<K, V> element : list) {
                    keys.add(element.getKey());
                }
            }
        }
        return keys;
    }
    /*
       функция values() проходит через все элементы массива slots,
       извлекает значения из каждого связанного списка и добавляет их в новый связанный список values,
       который затем возвращается как результат.
    */
    public Iterable<V> values() { // возвращает итерируемый объект, содержащий все значения
        LinkedList<V> values = new LinkedList<>();
        for (LinkedList<KeyValue<K, V>> list : slots) {
            if (list != null) {
                for (KeyValue<K, V> element : list) {
                    values.add(element.getValue());
                }
            }
        }
        return values;
    }

    @Override
    public Iterator<KeyValue<K, V>> iterator() {
        return new Iterator<KeyValue<K, V>>() {
            private int slotIndex = 0;
            private int keyIndex = 0;
            private KeyValue<K, V> current;

            @Override
            public boolean hasNext() {
                while (slotIndex < slots.length) {
                    if (slots[slotIndex] != null && keyIndex < slots[slotIndex].size()) {
                        return true;
                    }
                    slotIndex++;
                    keyIndex = 0;
                }
                return false;
            }

            @Override
            public KeyValue<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                current = slots[slotIndex].get(keyIndex);
                keyIndex++;
                return current;
            }
        };
    }
}