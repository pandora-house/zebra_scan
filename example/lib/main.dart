import 'package:flutter/material.dart';
import 'package:zebra_scan/zebra_scan.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: StreamBuilder(
            stream: ZebraScan.stream(),
            builder: (context, asyncSnapshot) {
              if (asyncSnapshot.hasData) {
                return Text(asyncSnapshot.data?.decodedData ?? 'error');
              }
              return Text('Press csan');
            }
          ),
        ),
      ),
    );
  }
}
