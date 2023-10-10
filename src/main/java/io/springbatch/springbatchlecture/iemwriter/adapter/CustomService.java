package io.springbatch.springbatchlecture.iemwriter.adapter;

public class CustomService<T> {

    public void customWrite(T item) {
        System.out.println(item);
    }

}
