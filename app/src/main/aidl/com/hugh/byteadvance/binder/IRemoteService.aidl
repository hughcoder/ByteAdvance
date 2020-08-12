// IRemoteService.aidl
package com.hugh.byteadvance.binder;

import com.hugh.byteadvance.binder.MyData;
interface IRemoteService {
  int getPid();
 MyData getMyData();
}
