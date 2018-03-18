package in.intellij.bottomnavigation.module.BottomNavigation.view;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Stack;

import in.intellij.bottomnavigation.R;
import in.intellij.bottomnavigation.module.BottomNavigation.common.BottomNavigationViewHelper;
import in.intellij.bottomnavigation.module.BottomNavigation.view.fragment.FragmentHome;
import in.intellij.bottomnavigation.module.BottomNavigation.view.fragment.FragmentMaintenance;
import in.intellij.bottomnavigation.module.BottomNavigation.view.fragment.FragmentMore;
import in.intellij.bottomnavigation.module.BottomNavigation.view.fragment.FragmentNotice;
import in.intellij.bottomnavigation.module.BottomNavigation.view.fragment.FragmentService;

public class MainActivity extends AppCompatActivity {

    private HashMap<String, Stack<Fragment>> stackFRAGMENT;

    public static final String TAB_HOME             = "TAB_HOME";
    public static final String TAB_MAINTENANCE      = "TAB_MAINTENANCE";
    public static final String TAB_SERVICE          = "TAB_SERVICE";
    public static final String TAB_NOTICE           = "TAB_NOTICE";
    public static final String TAB_MORE             = "TAB_MORE";

    private Fragment                fragment;
    private FragmentManager         fragmentManager;
    private BottomNavigationView    bottomNavigationView;

    private String strCurrentTAB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initOBJ();
    }

    private void initOBJ()
    {

        bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.inflateMenu(R.menu.navigation);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationViewControl);

        stackFRAGMENT = new HashMap<String, Stack<Fragment>>();

        stackFRAGMENT.put(TAB_HOME, new Stack<Fragment>());
        stackFRAGMENT.put(TAB_MAINTENANCE, new Stack<Fragment>());
        stackFRAGMENT.put(TAB_SERVICE, new Stack<Fragment>());
        stackFRAGMENT.put(TAB_NOTICE, new Stack<Fragment>());
        stackFRAGMENT.put(TAB_MORE, new Stack<Fragment>());

        bottomNavigationView.setSelectedItemId(R.id.tab_home);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationViewControl
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.tab_home:
                    selectedTab(TAB_HOME);
                    return true;
                case R.id.tab_maintenance:
                    selectedTab(TAB_MAINTENANCE);
                    return true;
                case R.id.tab_service:
                    selectedTab(TAB_SERVICE);
                    return true;
                case R.id.tab_notice:
                    selectedTab(TAB_NOTICE);
                    return true;
                case R.id.tab_more:
                    selectedTab(TAB_MORE);
                    return true;
            }
            return false;
        }

    };

    private void selectedTab(String tabId)
    {
        strCurrentTAB = tabId;

        if(stackFRAGMENT.get(tabId).size() == 0){
      /*
       *    First time this tab is selected. So add first fragment of that tab.
       *    Don't need animation, so that argument is false.
       *    We are adding a new fragment which is not present in stack. So add to stack is true.
       */
            switch (tabId) {
                case TAB_HOME:
                    pushFragments(tabId, new FragmentHome(), true);
                    break;
                case TAB_MAINTENANCE:
                    pushFragments(tabId, new FragmentMaintenance(), true);
                    break;
                case TAB_SERVICE:
                    pushFragments(tabId, new FragmentService(), true);
                    break;
                case TAB_NOTICE:
                    pushFragments(tabId, new FragmentNotice(), true);
                    break;
                case TAB_MORE:
                    pushFragments(tabId, new FragmentMore(), true);
                    break;
            }
         }else
         {
                /*
                *    We are switching tabs, and target tab is already has at least one fragment.
                *    No need of animation, no need of stack pushing. Just show the target fragment
                */
            pushFragments(tabId, stackFRAGMENT.get(tabId).lastElement(),false);
        }
    }

    public void pushFragments(String tag, Fragment fragment, boolean shouldAdd){
        if(shouldAdd)
        stackFRAGMENT.get(tag).push(fragment);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.main_container, fragment);
        ft.commit();
    }

    public void popFragments(){
  /*
   *    Select the second last fragment in current tab's stack..
   *    which will be shown after the fragment transaction given below
   */
        Fragment fragment = stackFRAGMENT.get(strCurrentTAB).elementAt(stackFRAGMENT.get(strCurrentTAB).size() - 2);

         /*pop current fragment from stack.. */
        stackFRAGMENT.get(strCurrentTAB).pop();

        /* We have the target fragment in hand.. Just show it.. Show a standard navigation animation*/
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.main_container, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if(stackFRAGMENT.get(strCurrentTAB).size() == 1){
            // We are already showing first fragment of current tab, so when back pressed, we will finish this activity..
            finish();
            return;
        }

         /* Goto previous fragment in navigation stack of this tab */
        popFragments();
    }



}
