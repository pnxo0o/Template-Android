package com.frojas.francisco.activities;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.frojas.francisco.hilos.VolleySingleton;
import com.frojas.francisco.pojo.Pelicula;

import java.util.ArrayList;

/**
 * Created by Francisco on 24/03/2015.
 */
public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolderPelicula> {

    private ArrayList<Pelicula> listPeliculas=new ArrayList<>();
    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;

    public Adaptador(Context context) {
        layoutInflater = LayoutInflater.from(context);
        volleySingleton=VolleySingleton.getInstance();
        imageLoader=volleySingleton.getImageLoader();

    }
    public void setPeliculaList(ArrayList<Pelicula> listPeliculas){
        this.listPeliculas=listPeliculas;
        notifyItemRangeChanged(0, listPeliculas.size());
    }


    public ArrayList<Pelicula> getListPeliculas() {
        return listPeliculas;
    }

    @Override
    public ViewHolderPelicula onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.pelicula_item, parent, false);
        ViewHolderPelicula viewHolder = new ViewHolderPelicula(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderPelicula holder, int position) {
        Pelicula peliculaActual = listPeliculas.get(position);
        holder.titulo.setText(peliculaActual.getTitle());
        holder.fechaLanzamiento.setText(peliculaActual.getReleaseDateTheater().toString());
        holder.puntuacion.setRating(peliculaActual.getAudienceScore() / 20.0F);
        String urlThumnail=peliculaActual.getUrlThumbnail();
        if(urlThumnail!=null)
        {
            imageLoader.get(urlThumnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.imagen.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return listPeliculas.size();
    }

    static class ViewHolderPelicula extends RecyclerView.ViewHolder  {

        private ImageView imagen;
        private TextView titulo;
        private TextView fechaLanzamiento;
        private RatingBar puntuacion;
        private CardView item;

        public ViewHolderPelicula(View itemView) {
            super(itemView);
            item = (CardView) itemView.findViewById(R.id.card);
            imagen = (ImageView) itemView.findViewById(R.id.movieThumbnail);
            titulo = (TextView) itemView.findViewById(R.id.movieTitle);
            fechaLanzamiento = (TextView) itemView.findViewById(R.id.movieReleaseDate);
            puntuacion = (RatingBar) itemView.findViewById(R.id.movieAudienceScore);
        }

    }
}
