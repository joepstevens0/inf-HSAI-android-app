package com.example.hsai_project.fragments.shoppingcart;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentManager;
        import androidx.fragment.app.FragmentTransaction;
        import androidx.lifecycle.LiveData;
        import androidx.lifecycle.Observer;
        import androidx.room.Room;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TextView;

        import com.example.hsai_project.ProductDatabase;
        import com.example.hsai_project.R;
        import androidx.lifecycle.LiveData;

        import java.util.ArrayList;
        import java.util.List;

public class ShoppingcartDelete extends AppCompatActivity {

    private LiveData<List<ShoppingcartItem>> m_data;
    private List<ShoppingcartDeleteItem> m_frags = new ArrayList<>();
    public static String EXTRA_REMOVED_ITEM_IDS = "com.example.hsai_project.fragments.shoppincart.REMOVED_IDS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingcart_delete);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getEntries();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setResult(Activity.RESULT_CANCELED);
        finish();
        return true;
    }

    public void getEntries(){
        ProductDatabase db = ProductDatabase.getInstance(this);

        m_data = db.productDao().getAllShoppingcartProducts();
        m_data.observe(this, new Observer<List<ShoppingcartItem>>() {
            @Override
            public void onChanged(List<ShoppingcartItem> productEntities) {
                addFrags();
                setButtons();
            }
        });
    }

    private void setButtons(){
        if(m_data.getValue() == null)
            return;
        Button b = (Button)findViewById(R.id.shoppingcart_delete_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> ids = new ArrayList<Integer>();
                for(int i = 0; i < m_data.getValue().size();++i){
                    if(m_frags.get(i).isActive()){
                        ids.add(m_frags.get(i).getItemId());
                    }
                }
                int[] r = new int[ids.size()];
                int i = 0;
                for (Integer e : ids)
                    r[i++] = e;
                returnResult(r);
            }
        });

    }

    public void addFrags(){
        if(m_data.getValue() == null)
            return;

        for(Fragment frag: m_frags) {
            getSupportFragmentManager().beginTransaction().remove(frag).commit();
        }
        for(int i = 0; i < m_data.getValue().size();++i) {
            ShoppingcartDeleteItem frag = new ShoppingcartDeleteItem(m_data.getValue().get(i));

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            transaction.add(R.id.shoppingcart_delete_fragmentcontainer, frag);
            transaction.commit();
            m_frags.add(frag);
        }
    }

    private void returnResult(int[] ids){
        Intent i = new Intent();
        i.putExtra(EXTRA_REMOVED_ITEM_IDS, ids);
        setResult(Activity.RESULT_OK, i);
        finish();
    }
}
