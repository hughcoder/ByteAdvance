// ICompletedListener.aidl
package com.hugh.byteadvance.binder;

import com.hugh.byteadvance.binder.MyData;

interface ICompletedListener {
   void onOperationCompleted(in MyData result);
}
