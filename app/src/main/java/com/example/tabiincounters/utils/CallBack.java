package com.example.tabiincounters.utils;

public class CallBack {
    public static void addCallback(com.example.tabiincounters.utils.CallbackInterface callback){
        com.example.tabiincounters.utils.CallbackInterface.callbacks.add(callback);
    }
    public static void runAllCallbacks(){
        for(com.example.tabiincounters.utils.CallbackInterface c : com.example.tabiincounters.utils.CallbackInterface.callbacks){
            c.call();
        }
    }
}