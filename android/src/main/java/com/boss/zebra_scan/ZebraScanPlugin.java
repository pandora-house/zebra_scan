package com.boss.zebra_scan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;

/**
 * ZebraScanPlugin
 */
public class ZebraScanPlugin implements FlutterPlugin {
    private static final String ACTION_NAME = "com.boss.zebra.ACTION";

    private EventChannel eventChannel;
    private EventChannel.StreamHandler eventStreamHandler;
    private EventChannel.EventSink sink;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        IntentFilter filter = new IntentFilter();
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        filter.addAction(ACTION_NAME);

        Context context = flutterPluginBinding.getApplicationContext();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(myBroadcastReceiver, filter, Context.RECEIVER_EXPORTED);
        } else {
            context.registerReceiver(myBroadcastReceiver, filter);
        }

        eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "zebra_scan");

        eventStreamHandler = new EventChannel.StreamHandler() {
            @Override
            public void onListen(Object arguments, EventChannel.EventSink events) {
                sink = events;
            }

            @Override
            public void onCancel(Object arguments) {

            }
        };

        eventChannel.setStreamHandler(eventStreamHandler);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        binding.getApplicationContext().unregisterReceiver(myBroadcastReceiver);
    }

    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //  Bundle b = intent.getExtras();
            //  This is useful for debugging to verify the format of received intents from DataWedge
            //for (String key : b.keySet())
            //{
            //    Log.v(LOG_TAG, key);
            //}

            if (action.equals(ACTION_NAME)) {
                //  Received a barcode scan
                try {
                    String decodedSource = intent.getStringExtra("com.symbol.datawedge.source");
                    String decodedData = intent.getStringExtra("com.symbol.datawedge.data_string");
                    String decodedLabelType = intent.getStringExtra("com.symbol.datawedge.label_type");

                    JSONObject json = new JSONObject();
                    try {
                        json.put("decodedSource", decodedSource);
                        json.put("decodedData", decodedData);
                        json.put("decodedLabelType", decodedLabelType);

                        sink.success(json.toString());
                    } catch (Exception e) {
                        sink.success(e.toString());
                    }
                } catch (Exception e) {
                    sink.success(e.toString());
                }
            }
        }
    };
}