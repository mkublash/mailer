package tbc.leasing.mail;

import java.io.File;
import java.util.Objects;

public class Contact {
    private String recepientAddress;
    private String imagePath;

    public Contact(String recepientAddress, String imagePath) {
        this.recepientAddress = recepientAddress;
        this.imagePath = imagePath;
    }

    public String getRecepientAddress() {
        return recepientAddress;
    }

    public void setRecepientAddress(String recepientAddress) {
        this.recepientAddress = recepientAddress;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(recepientAddress, contact.recepientAddress) &&
                Objects.equals(imagePath, contact.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recepientAddress, imagePath);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "recepientAddress='" + recepientAddress + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }

    public File getImageFile() {
        return new File(this.imagePath);
    }
}
