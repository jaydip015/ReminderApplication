package com.example.reminderapplication.Servicies;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.reminderapplication.R;
import com.example.reminderapplication.RDB.AppDb;
import com.example.reminderapplication.RDB.DAO;

public  class AdapterServiceFWidget extends RemoteViewsService {
    String data[]={};
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ServiceFactory(getApplicationContext(),intent);
    }
    public class ServiceFactory implements RemoteViewsFactory{
        Context context;
        int appwidgetId;

        public ServiceFactory(Context applicationContext, Intent intent) {
            context=applicationContext;

        }

        @Override
        public void onCreate() {
            Log.d("update","on create");
            AppDb appDb=AppDb.getInstance(context);
            DAO dao=appDb.dao();
            data=dao.ReminderNames();

        }

        @Override
        public void onDataSetChanged(){
            Log.d("update","finnaly");
            AppDb appDb=AppDb.getInstance(context);
            DAO dao=appDb.dao();
            data=dao.ReminderNames();
        }

        @Override
        public void onDestroy(){}

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews views=new RemoteViews(context.getPackageName(), R.layout.card_view_widget);
            views.setTextViewText(R.id.widget_tv,data[i]);
            return views;
        }

        /**
         * <ImageButton
         *             android:layout_width="wrap_content"
         *             android:layout_height="wrap_content"
         *             android:src="@drawable/baseline_delete_24"
         *             app:layout_constraintEnd_toEndOf="parent"
         *             app:layout_constraintTop_toTopOf="parent"/>
         *
         */
        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
