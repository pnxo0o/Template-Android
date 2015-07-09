package com.frojas.francisco.activities;

import android.content.Context;
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
public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolderBoxOffice> {

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

    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_movie_box_office, parent, false);
        ViewHolderBoxOffice viewHolder = new ViewHolderBoxOffice(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderBoxOffice holder, int position) {
        Pelicula currentPelicula=listPeliculas.get(position);
        holder.PeliculaTitle.setText(currentPelicula.getTitle());
        holder.PeliculaReleaseDate.setText(currentPelicula.getReleaseDateTheater().toString());
        holder.PeliculaAudienceScore.setRating(currentPelicula.getAudienceScore()/20.0F);
        String urlThumnail=currentPelicula.getUrlThumbnail();
        if(urlThumnail!=null)
        {
            imageLoader.get(urlThumnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.PeliculaThumbnail.setImageBitmap(response.getBitmap());
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

    static class ViewHolderBoxOffice extends RecyclerView.ViewHolder {

        ImageView PeliculaThumbnail;
        TextView PeliculaTitle;
        TextView PeliculaReleaseDate;
        RatingBar PeliculaAudienceScore;

        public ViewHolderBoxOffice(View itemView) {
            super(itemView);
            PeliculaThumbnail = (ImageView) itemView.findViewById(R.id.movieThumbnail);
            PeliculaTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            PeliculaReleaseDate = (TextView) itemView.findViewById(R.id.movieReleaseDate);
            PeliculaAudienceScore = (RatingBar) itemView.findViewById(R.id.movieAudienceScore);
        }
    }
}
