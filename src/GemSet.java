import java.util.*;

// Узагальнений клас GemSet для роботи з коштовним камінням,
// що реалізує інтерфейс Set з використанням двозв'язного списку
public class GemSet<T extends Gem> implements Set<T> {

    // Внутрішній клас Node представляє елемент двозв'язного списку
    private class Node {
        T data;      // Дані, що зберігаються у вузлі
        Node next;   // Посилання на наступний вузол
        Node prev;   // Посилання на попередній вузол

        // Конструктор, що ініціалізує дані вузла
        Node(T data) {
            this.data = data;
        }
    }

    private Node head; // Початковий вузол списку
    private Node tail; // Кінцевий вузол списку
    private int size;  // Кількість елементів у колекції

    // Порожній конструктор створює пустий список
    public GemSet() {
        head = null;
        tail = null;
        size = 0;
    }

    // Конструктор, що приймає один об'єкт типу Gem
    public GemSet(T gem) {
        this(); // Виклик порожнього конструктора для ініціалізації списку
        add(gem); // Додає переданий об'єкт у колекцію
    }

    // Конструктор, що приймає колекцію об'єктів типу Gem
    public GemSet(Collection<? extends T> gems) {
        this(); // Ініціалізація порожнього списку
        addAll(gems); // Додає всі елементи переданої колекції
    }

    // Метод для додавання елемента до колекції
    @Override
    public boolean add(T gem) {
        // Перевірка наявності об'єкта для уникнення дублікатів
        if (contains(gem)) return false;
        // Створення нового вузла
        Node newNode = new Node(gem);
        // Якщо список порожній, новий вузол стає головою і хвостом
        if (head == null) {
            head = tail = newNode;
        } else {
            // Додає вузол у кінець списку
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++; // Збільшення розміру колекції
        return true;
    }

    // Метод для перевірки, чи містить колекція вказаний об'єкт
    @Override
    public boolean contains(Object o) {
        Node current = head;
        while (current != null) {
            if (current.data.equals(o)) return true; // Повертає true, якщо об'єкт знайдено
            current = current.next;
        }
        return false; // Повертає false, якщо об'єкт не знайдено
    }

    // Метод для видалення об'єкта з колекції
    @Override
    public boolean remove(Object o) {
        Node current = head;
        while (current != null) {
            if (current.data.equals(o)) {
                // Зміна зв'язків для видалення вузла зі списку
                if (current.prev != null) current.prev.next = current.next;
                if (current.next != null) current.next.prev = current.prev;
                if (current == head) head = current.next; // Оновлення голови, якщо видаляється перший вузол
                if (current == tail) tail = current.prev; // Оновлення хвоста, якщо видаляється останній вузол
                size--; // Зменшення розміру колекції
                return true; // Повертає true, якщо об'єкт видалено
            }
            current = current.next;
        }
        return false; // Повертає false, якщо об'єкт не знайдено
    }

    // Метод для отримання розміру колекції
    @Override
    public int size() {
        return size;
    }

    // Метод для перевірки, чи порожня колекція
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Очищення колекції
    @Override
    public void clear() {
        head = tail = null; // Видалення всіх зв'язків
        size = 0;
    }

    // Метод для додавання всіх елементів з іншої колекції
    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        // Додає кожен елемент з колекції, змінюючи modified на true при успішному додаванні
        for (T gem : c) {
            if (add(gem)) modified = true;
        }
        return modified;
    }

    // Перетворення колекції у масив об'єктів
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int index = 0;
        Node current = head;
        while (current != null) {
            array[index++] = current.data; // Додає елементи до масиву
            current = current.next;
        }
        return array;
    }

    // Перетворення колекції у типізований масив
    @Override
    public <U> U[] toArray(U[] a) {
        if (a.length < size) {
            a = (U[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }
        int i = 0;
        Object[] result = a;
        for (Node x = head; x != null; x = x.next) {
            result[i++] = x.data;
        }
        if (a.length > size) a[size] = null;
        return a;
    }

    // Інші методи інтерфейсу Set, що не реалізовані
    @Override public boolean containsAll(Collection<?> c) { /* реалізація */ return false; }
    @Override public boolean removeAll(Collection<?> c) { /* реалізація */ return false; }
    @Override public boolean retainAll(Collection<?> c) { /* реалізація */ return false; }
    @Override public Iterator<T> iterator() { /* реалізація */ return null; }

    // Виконавчий метод для тестування
    public static void main(String[] args) {
        // Створення та тестування трьох об'єктів GemSet з різними конструкторами
        GemSet<Gem> gemSet1 = new GemSet<>();
        GemSet<Gem> gemSet2 = new GemSet<>(new Diamond(1.5, 5000, 95));
        GemSet<Gem> gemSet3 = new GemSet<>(Arrays.asList(
                new Ruby(2.0, 3000, 80),
                new Topaz(3.0, 500, 60)
        ));

        gemSet1.add(new Diamond(1.5, 5000, 95));
        gemSet1.add(new Ruby(3.0, 3000, 80));

        System.out.println("Розмір gemSet1: " + gemSet1.size());
        System.out.println("Розмір gemSet2: " + gemSet2.size());
        System.out.println("Розмір gemSet3: " + gemSet3.size());

        gemSet1.remove(new Ruby(2.0, 3000, 80));
        System.out.println("Розмір gemSet1 після видалення: " + gemSet1.size());

        gemSet1.remove(new Ruby(3.0, 3000, 80));
        System.out.println("Розмір gemSet1 після видалення: " + gemSet1.size());
    }
}
