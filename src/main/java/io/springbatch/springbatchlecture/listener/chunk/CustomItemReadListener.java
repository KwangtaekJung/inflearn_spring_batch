package io.springbatch.springbatchlecture.listener.chunk;


import org.springframework.batch.core.ItemReadListener;

public class CustomItemReadListener implements ItemReadListener<Integer> {
    @Override
    public void beforeRead() {
        System.out.println(">> before Read");
    }

    @Override
    public void afterRead(Integer integer) {
        System.out.println(">> after Read");

    }

    @Override
    public void onReadError(Exception e) {
        System.out.println(">> onRead Error");
    }
}
