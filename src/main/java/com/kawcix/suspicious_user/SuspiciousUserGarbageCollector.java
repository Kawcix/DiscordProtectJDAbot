package com.kawcix.suspicious_user;

public class SuspiciousUserGarbageCollector implements Runnable {

    final Thread thread;

    public SuspiciousUserGarbageCollector() {
        thread = new Thread(this, "SuspiciousUserGarbageCollectorTask");
        thread.start();
    }

    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            SuspiciousUser.suspiciousUserList.removeIf(i -> i.isShouldRemove());


        }
    }
}
