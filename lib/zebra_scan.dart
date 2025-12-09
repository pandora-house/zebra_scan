import 'dart:async';
import 'dart:convert';

import 'package:flutter/services.dart';

class ZebraData {
  const ZebraData({
    required this.decodedData,
    required this.decodedLabelType,
    required this.decodedSource,
  });

  factory ZebraData.fromString(String value) {
    final json = jsonDecode(value);
    return ZebraData(
      decodedData: json['decodedData'],
      decodedLabelType: json['decodedLabelType'],
      decodedSource: json['decodedSource'],
    );
  }

  final String decodedSource;
  final String decodedData;
  final String decodedLabelType;
}

class ZebraScan {
  static const EventChannel _eventChannel = EventChannel('zebra_scan');

  static Stream<ZebraData> stream() => _eventChannel
      .receiveBroadcastStream()
      .map((e) => ZebraData.fromString(e));
}
