import {error} from "@/utils";

class WebSocketManager {
  constructor() {
    this.webSocketInstance = null; // WebSocket实例对象，使用更清晰的命名
    this.reconnectTimer = null; // 断线重连后，存储延迟请求的代码，用于延迟重连
    this.isConnected = false; // 连接标识，使用更符合语义的命名
    this.onConnectCallback = null; // 连接成功的回调函数
    this.onMessageCallback = null; // 收到消息的回调函数
    this.onCloseCallback = null; // 连接关闭的回调函数
    this.heartbeatCheck = {
      interval: 5000, // 每段时间发送一次心跳包的时间间隔，这里设置为5秒，使用更表意明确的变量名
      timer: null, // 延时发送消息对象（启动心跳新建这个对象，收到消息后重置对象）
      start: () => {
        if (this.isConnected) {
          console.log('发送WebSocket心跳');
          this.webSocketInstance.send('ping');
        }
      },
      reset: () => {
        clearTimeout(this.heartbeatCheck.timer);
        this.heartbeatCheck.timer = setTimeout(() => {
          this.heartbeatCheck.start();
        }, this.heartbeatCheck.interval);
      }
    };
  }

  // 连接WebSocket的方法
  connect(url) {
    try {
      if (this.isConnected) {
        return;
      }
      console.log("连接WebSocket");
      this.webSocketInstance = new WebSocket(url);

      const timeoutTimer = setTimeout(() => {
        console.log(this.isConnected)
        if (!this.isConnected) {
          error("WebSocket连接超时")
        }
        clearTimeout(timeoutTimer)
      }, 5000);


      this.webSocketInstance.onmessage = (event) => {
        const data = event.data;
        if (data === 'pong') {
          // 重新开启心跳定时
          this.heartbeatCheck.reset();
        } else {
          // 其他消息转发出去
          console.log("收到消息:", data);
          this.onMessageCallback && this.onMessageCallback(JSON.parse(data));
        }
      };
      this.webSocketInstance.onclose = (event) => {
        console.log('WebSocket连接关闭');
        this.isConnected = false;
        this.onCloseCallback && this.onCloseCallback(event);
        this.reconnect(url);
      };
      this.webSocketInstance.onopen = () => {
        console.log("WebSocket连接成功");
        this.isConnected = true;
        this.heartbeatCheck.start();

        this.onConnectCallback && this.onConnectCallback();
      };

      // 连接发生错误的回调方法
      this.webSocketInstance.onerror = () => {
        console.log('WebSocket连接发生错误');
        this.isConnected = false;
        this.reconnect(url);
      };
    } catch (error) {
      console.log("尝试创建连接失败");
      this.reconnect(url);
    }
  }

  // 定义重连函数
  reconnect(url) {
    console.log("尝试重新连接");
    if (this.isConnected) {
      return;
    }
    if (this.reconnectTimer) {
      clearTimeout(this.reconnectTimer);
    }
    this.reconnectTimer = setTimeout(() => {
      this.connect(url);
    }, 15000);
  }

  // 设置关闭连接的方法
  close(code) {
    this.webSocketInstance && this.webSocketInstance.close(code);
  }

  // 发送消息的方法
  sendMessage(messageData) {
    if (this.webSocketInstance && this.webSocketInstance.readyState === WebSocket.OPEN) {
      // 若是ws开启状态
      this.webSocketInstance.send(messageData);
    } else if (this.webSocketInstance && this.webSocketInstance.readyState === WebSocket.CONNECTING) {
      setTimeout(() => {
        this.sendMessage(messageData);
      }, 1000);
    } else {
      setTimeout(() => {
        this.sendMessage(messageData);
      }, 1000);
    }
  }

  // 设置连接成功回调函数的方法
  onConnect(callback) {
    this.onConnectCallback = callback;
  }

  // 设置收到消息回调函数的方法
  onMessage(callback) {
    this.onMessageCallback = callback;
  }

  // 设置连接关闭回调函数的方法
  onClose(callback) {
    this.onCloseCallback = callback;
  }
}

const webSocketManager = new WebSocketManager();
export default webSocketManager;
