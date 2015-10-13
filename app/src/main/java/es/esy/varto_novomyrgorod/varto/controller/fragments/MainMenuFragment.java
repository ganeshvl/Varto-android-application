package es.esy.varto_novomyrgorod.varto.controller.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import es.esy.varto_novomyrgorod.varto.R;
import es.esy.varto_novomyrgorod.varto.model.database.DBHelper;
import es.esy.varto_novomyrgorod.varto.model.database.DBInfomationProvider;
import es.esy.varto_novomyrgorod.varto.model.pojo.InformationObject;

public class MainMenuFragment extends Fragment implements View.OnClickListener {
    private LinearLayout newsPlusLayout;
    private LinearLayout sharesPlusLayout;
    private LinearLayout newsDishesLayout;
    private LinearLayout sharesDishesLayout;
    private TextView countNewsPlus;
    private TextView countSharesPlus;
    private TextView countNewsDishes;
    private TextView countSharesDishes;
    private LinearLayout logo;
    private TextView buttonPlus;
    private TextView buttonDishes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, null);
    }

    @Override
    public void onStart() {
        super.onStart();

        logo = (LinearLayout) getActivity().findViewById(R.id.logo_layout);
        buttonPlus = (TextView) getActivity().findViewById(R.id.textview_plus);
        buttonDishes = (TextView) getActivity().findViewById(R.id.textview_dishes);

        newsPlusLayout = (LinearLayout) getActivity().findViewById(R.id.info_news_plus);
        newsDishesLayout = (LinearLayout) getActivity().findViewById(R.id.info_news_dishes);
        sharesPlusLayout = (LinearLayout) getActivity().findViewById(R.id.info_shares_plus);
        sharesDishesLayout = (LinearLayout) getActivity().findViewById(R.id.info_shares_dishes);

        countNewsPlus = (TextView) getActivity().findViewById(R.id.textview_count_news_plus);
        countSharesPlus = (TextView) getActivity().findViewById(R.id.textview_count_shares_plus);
        countNewsDishes = (TextView) getActivity().findViewById(R.id.textview_count_news_dishes);
        countSharesDishes = (TextView) getActivity().findViewById(R.id.textview_count_shares_dishes);


        DBHelper dbHelper = new DBHelper(getActivity());
        DBInfomationProvider dbInfomationProvider = new DBInfomationProvider(dbHelper);
        showInformation(dbInfomationProvider.getInformationFromSQLDatabase());
        logo.setVisibility(View.VISIBLE);
        ImageView imageViewPlus = (ImageView) getActivity().findViewById(R.id.imageview_plus);
        ImageView imageViewDishes = (ImageView) getActivity().findViewById(R.id.imageview_dishes);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(400, true,true,true))
                .build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage("assets://images/plus.JPG", imageViewPlus, options);
        imageLoader.displayImage("assets://images/dishes.jpg", imageViewDishes, options);
    }

    @Override
    public void onResume() {
        super.onResume();
        buttonPlus.setOnClickListener(this);
        buttonDishes.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        logo.setVisibility(View.INVISIBLE);
        buttonPlus.setOnClickListener(null);
        buttonDishes.setOnClickListener(null);
    }


    @Override
    public void onClick(View v) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        switch (v.getId()) {
            case R.id.textview_plus: {
                MenuShopFragment menuShopFragment = MenuShopFragment.newInstance("plus",
                        0,
                        0);
                transaction.replace(R.id.container, menuShopFragment).commit();
            }
            break;
            case R.id.textview_dishes: {
                MenuShopFragment menuShopFragment = MenuShopFragment.newInstance("dishes",
                        0,
                        0);
                transaction.replace(R.id.container, menuShopFragment).commit();
            }
            break;
        }
    }

    public void showInformation(InformationObject object) {
        Animation slide = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.abc_slide_in_bottom);
        Log.i("DBG", "goods = "+object.getAmountOfGoodsPlus() +", news = " +object.getAmountOfNewsPlus());
        Log.i("DBG", "goods = "+object.getAmountOfGoodsDishes() +", news = " +object.getAmountOfNewsDishes());
        int newsPlus = object.getAmountOfNewsPlus();
        int newsDishes = object.getAmountOfNewsDishes();
        int goodsPlus = object.getAmountOfGoodsPlus();
        int goodsDishes = object.getAmountOfGoodsDishes();
        if (newsPlus > 0) {
            countNewsPlus.setText("(+" + newsPlus + ") новин");
            newsPlusLayout.setVisibility(View.VISIBLE);
            newsPlusLayout.startAnimation(slide);
        }
        if (newsDishes > 0) {
            countNewsDishes.setText("(+" + newsDishes + ") новин");
            newsDishesLayout.setVisibility(View.VISIBLE);
            newsDishesLayout.startAnimation(slide);
        }
        if (goodsPlus > 0) {
            countSharesPlus.setText("(+" + goodsPlus + ") товар");
            sharesPlusLayout.setVisibility(View.VISIBLE);
            sharesPlusLayout.startAnimation(slide);
        }
        if (goodsDishes > 0) {
            countSharesDishes.setText("(+" + goodsDishes + ") товар");
            sharesDishesLayout.setVisibility(View.VISIBLE);
            sharesDishesLayout.startAnimation(slide);
        }
    }

    public void hideAllInforamation() {
        newsPlusLayout.setVisibility(View.GONE);
        newsDishesLayout.setVisibility(View.GONE);
        sharesPlusLayout.setVisibility(View.GONE);
        sharesDishesLayout.setVisibility(View.GONE);
    }
}