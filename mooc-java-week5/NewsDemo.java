/**
 *  一个简单的新闻事件侦听演示程序
 * @author tds
 * @author 修改者：carryonmooc41
 */

public class NewsDemo {
	public static void main(String[] args) {
		
		//模拟有一家新闻社
		NewsAgency bbc = new NewsAgency("BBC");
		//订阅该社的新闻
		bbc.addListener(new MyListener());
		
		//TODO 请在这里再加入一个Listener
		// 1、匿名内部类
		bbc.addListener(new Listener() {
			@Override
			public void newsArrived(NewsEvent e) {
				if (e.level > 5) {
					System.out.println("attention, there was a " + e.text);
				}
			}
		});
		// 2、lambda
		bbc.addListener((e) -> {if (e.level > 5) System.out.println("attention, there was a " + e.text);});
		
		//新闻社启动其工作流程
		bbc.start();				
	}
}


/**
 * 事件信息
 */
class NewsEvent{
	Object source;  //事件来源
	String text;    //事件内容
	int level;      //事件级别
	NewsEvent(Object source, String text, int level){
		this.source=source;
		this.text = text;
		this.level = level;		
	}
}
interface Listener{
	void newsArrived(NewsEvent e);
}

/**
 * 新闻机构
 */
class NewsAgency {
	String name; //机构名
	public NewsAgency(String name) {
		this.name = name;		
	}
	Listener[] listeners = new  Listener[100]; //侦听者（订阅者）
	int listenerCnt = 0; //已有的订阅者
	
	//加入一个订阅者
	void addListener(Listener oneListener){
		if(listenerCnt<listeners.length){
			listeners[listenerCnt] = oneListener;
			listenerCnt++;
		}		
	}
	
	//模拟一个事件发生，并通知所有的订阅者
	void start(){
		NewsEvent event = new NewsEvent("Mr. Joan", "Bombing", 9 );
		
		for(int i=0; i<listenerCnt; i++) {
			listeners[i].newsArrived(event);
		}
		
	}
}

/**
 * 实现一个订阅者
 */
class MyListener implements Listener{
	//当接收到消息后，进行一些显示
	public void newsArrived(NewsEvent e){
		if( e.level>5) System.out.println("warning :");
		System.out.println("please note," + e.text + " happed!");
	}
}




