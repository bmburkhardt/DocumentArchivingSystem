<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Document extends Model
{
    protected $fillable = [
        'photo_id',
        'title',
        'path',
        'summary',
        'user_id'
    ];
}
