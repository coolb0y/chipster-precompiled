import React, { useState } from 'react';
import { View, Button, Text } from 'react-native';
import { NativeModules } from 'react-native';

const { LighttpdModule } = NativeModules;

export default function App() {
  const [status, setStatus] = useState('Not Started');

  const startServer = () => {
    // Define the type for the callback parameter
    LighttpdModule.startLighttpd((message: string) => {
      setStatus(message);
    });
  };

  return (
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <Button title="Start Lighttpd Server" onPress={startServer} />
      <Text>{status}</Text>
    </View>
  );
}
