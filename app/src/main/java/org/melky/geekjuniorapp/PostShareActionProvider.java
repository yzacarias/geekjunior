package org.melky.geekjuniorapp;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.support.v7.internal.widget.ActivityChooserModel;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/**
 * Created by Administrador on 13/05/2015.
 */
public class PostShareActionProvider extends ShareActionProvider {
    private Context mContext;
    private int mMaxShownActivityCount = 4;
    private String mShareHistoryFileName = "share_history.xml";
    private final ShareMenuItemOnMenuItemClickListener mOnMenuItemClickListener =
            new ShareMenuItemOnMenuItemClickListener();

    /**
     * Creates a new instance.
     *
     * @param context Context for accessing resources.
     */
    public PostShareActionProvider(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View onCreateActionView() {
        return null;
    }

    @Override
    public void onPrepareSubMenu(SubMenu subMenu) {
        // Clear since the order of items may change.
        subMenu.clear();

        ActivityChooserModel dataModel = ActivityChooserModel.get(mContext, mShareHistoryFileName);
        PackageManager packageManager = mContext.getPackageManager();

        final int expandedActivityCount = dataModel.getActivityCount();
        final int collapsedActivityCount = Math.min(expandedActivityCount, mMaxShownActivityCount);

        // Populate the sub-menu with a sub set of the activities.
        for (int i = 0; i < expandedActivityCount; i++) {
            ResolveInfo activity = dataModel.getActivity(i);
            String packageName = activity.activityInfo.packageName;
            if(packageName.contains("twitter") || packageName.contains("facebook.katana") || packageName.contains("plus")) {
                subMenu.add(0, i, i, activity.loadLabel(packageManager))
                        .setIcon(activity.loadIcon(packageManager))
                        .setOnMenuItemClickListener(mOnMenuItemClickListener);
            }
        }

       /* if (collapsedActivityCount < expandedActivityCount) {
            // Add a sub-menu for showing all activities as a list item.
            SubMenu expandedSubMenu = subMenu.addSubMenu(Menu.NONE, collapsedActivityCount,
                    collapsedActivityCount,
                    mContext.getString(android.support.v7.appcompat.R.string.abc_activity_chooser_view_see_all));
            for (int i = 0; i < expandedActivityCount; i++) {
                ResolveInfo activity = dataModel.getActivity(i);
                String packageName = activity.activityInfo.packageName;
                if(packageName.contains("twitter") || packageName.contains("facebook")) {
                    expandedSubMenu.add(0, i, i, activity.loadLabel(packageManager))
                            .setIcon(activity.loadIcon(packageManager))
                            .setOnMenuItemClickListener(mOnMenuItemClickListener);
                }
            }
        }*/
    }
    private class ShareMenuItemOnMenuItemClickListener implements MenuItem.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            ActivityChooserModel dataModel = ActivityChooserModel.get(mContext,
                    mShareHistoryFileName);
            final int itemId = item.getItemId();
            Intent launchIntent = dataModel.chooseActivity(itemId);
            if (launchIntent != null) {
                final String action = launchIntent.getAction();
                if (Intent.ACTION_SEND.equals(action) ||
                        Intent.ACTION_SEND_MULTIPLE.equals(action)) {
                    updateIntent(launchIntent);
                }
                mContext.startActivity(launchIntent);
            }
            return true;
        }
    }

    private void updateIntent(Intent intent) {
        if (Build.VERSION.SDK_INT >= 21) {
            // If we're on Lollipop, we can open the intent as a document
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        } else {
            // Else, we will use the old CLEAR_WHEN_TASK_RESET flag
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        }
    }
}


