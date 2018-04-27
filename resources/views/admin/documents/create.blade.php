@extends('layouts.admin')

@section('content')
    <h1>Upload Document</h1>
    <div class="row">
        {!! Form::open(['method'=>'POST', 'action'=> 'AdminDocumentsController@store', 'files'=>true]) !!}

        <div class="form-group">
            {!! Form::label('title', 'Title:') !!}
            {!! Form::text('title', null, ['class'=>'form-control'])!!}
        </div>

        <div class="form-group">
            {!! Form::label('category_id', 'Category:') !!}
            {!! Form::select('category_id', array(0=>'Article',
                                                  1=>'Editorial',
                                                  2=>'Essay',
                                                  3=>'Poem',
                                                  4=>'Recipe',
                                                  5=>'Resume',
                                                  6=>'Misc.')
                                                  , null, ['class'=>'form-control'])!!}
        </div>

        <div class="form-group">
            {!! Form::label('summary', 'Summary:') !!}
            {!! Form::textarea('summary', null, ['class'=>'form-control', 'rows'=>3])!!}
        </div>

        <div class="form-group">
            {!! Form::label('path', 'Upload:') !!}
            {!! Form::file('path', null, ['class'=>'form-control'])!!}
        </div>

        <div class="form-group">
            {!! Form::submit('Upload Document', ['class'=>'btn btn-primary']) !!}
        </div>

        {!! Form::close() !!}{!! Form::close() !!}
    </div>


    <div class="row">
        @include('includes.form_error')
    </div>
@stop