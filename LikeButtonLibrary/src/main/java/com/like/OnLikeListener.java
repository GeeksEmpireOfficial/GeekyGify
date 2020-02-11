/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/10/20 3:04 PM
 * Last modified 3/3/18 8:09 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package com.like;

/**
 * Created by Joel on 23/12/2015.
 */
public interface OnLikeListener {
    void liked(LikeButton likeButton);
    void unLiked(LikeButton likeButton);
}
