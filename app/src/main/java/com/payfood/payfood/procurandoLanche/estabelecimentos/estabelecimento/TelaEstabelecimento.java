package com.payfood.payfood.procurandoLanche.estabelecimentos.estabelecimento;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.payfood.payfood.R;
import com.payfood.payfood.comunicacaoExterna.RestClient;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import framework.Tela;
import minhaLang.Util;

public class TelaEstabelecimento extends Tela {

    ImageView bannerEstabelecimento;
    ListView listaProdutos;

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_estabelecimento);
        // binds
        bannerEstabelecimento = (ImageView) findViewById(R.id.banner_estabelecimento);
        listaProdutos = (ListView) findViewById(R.id.lista_produtos);

        // intent params
        String nome = getIntent().getStringExtra("nome");
        int id = getIntent().getIntExtra("id", 0);

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(nome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // init
        carregarEstabelecimento(id);

        // produtos
        carregarProdutos();
    }

    private void carregarProdutos() {
        String[] itens = {"TESTE","TESTE","TESTE","TESTE","TESTE","TESTE","TESTE","TESTE","TESTE","TESTE","TESTE"};
        ArrayAdapter<String > adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itens);
        listaProdutos.setAdapter(adapter);
    }

    private void carregarEstabelecimento(int id) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        RestClient.get("/estabelecimento", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String img = response.getString("img");
                    Util.glidImage(bannerEstabelecimento, img, TelaEstabelecimento.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}