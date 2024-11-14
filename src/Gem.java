import java.util.Objects;

class Gem {
    private final double weight; // вага в каратах
    private final double pricePerCarat; // ціна за карат
    private final double transparency; // прозорість у відсотках

    public Gem(double weight, double pricePerCarat, double transparency) {
        this.weight = weight;
        this.pricePerCarat = pricePerCarat;
        this.transparency = transparency;
    }

    public double getWeight() {
        return weight;
    }

    public double getPrice() {
        return pricePerCarat * weight;
    }

    public double getTransparency() {
        return transparency;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Gem gem = (Gem) obj;
        return Double.compare(gem.weight, weight) == 0 &&
                Double.compare(gem.pricePerCarat, pricePerCarat) == 0 &&
                Double.compare(gem.transparency, transparency) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, pricePerCarat, transparency);
    }
}
