// IRemoteService.aidl
package com.hugh.byteadvance.binder;

import com.hugh.byteadvance.binder.MyData;
import com.hugh.byteadvance.binder.ICompletedListener;
interface IRemoteService {
      int getPid();
      MyData getMyData();
      void operation(in MyData parameter1 );
      void registerListener(in ICompletedListener listener);

      void unregisterListener(in ICompletedListener listener);
}
