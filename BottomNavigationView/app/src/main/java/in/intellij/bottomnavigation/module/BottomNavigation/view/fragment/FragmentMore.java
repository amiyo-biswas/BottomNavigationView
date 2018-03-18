package in.intellij.bottomnavigation.module.BottomNavigation.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import in.intellij.bottomnavigation.R;
import in.intellij.bottomnavigation.module.BottomNavigation.view.MainActivity;

/**
 * Created by Intellij Amiya on 18/3/18.
 * Who Am I- https://stackoverflow.com/users/3395198/
 * A good programmer is someone who looks both ways before crossing a One-way street.
 * Kindly follow https://source.android.com/setup/code-stylei
 */

public class FragmentMore extends Fragment implements View.OnClickListener {

    public FragmentMore() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_more, container, false);
        Button aboutBtn = rootView.findViewById(R.id.buttonTest);
        aboutBtn.setOnClickListener(this);
        return rootView;

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.buttonTest:
                ((MainActivity)getActivity()).pushFragments(MainActivity.TAB_MORE, new FragmentMoreList(),true);

                break;

        }
    }

}