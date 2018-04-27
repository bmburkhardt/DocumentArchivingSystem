@extends('layouts.admin')

@section('content')
    <h1>Edit Document</h1>
    <div class="row">
        {!! Form::model($document, ['method'=>'PATCH', 'action'=> ['AdminDocumentsController@update', $document->id], 'files'=>true]) !!}

        <div class="form-group">
            {!! Form::label('title', 'Title:') !!}
            {!! Form::text('title', null, ['class'=>'form-control'])!!}
        </div>

        <div class="form-group">
            {!! Form::label('category_id', 'Category:') !!}
            {!! Form::select('category_id', $categories, null, ['class'=>'form-control'])!!}
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
            {!! Form::submit('Update Document', ['class'=>'btn btn-primary col-sm-1']) !!}
        </div>

        {!! Form::close() !!}

        {!! Form::open(['method'=>'DELETE', 'action'=> ['AdminDocumentsController@destroy', $document->id]]) !!}

        <div class="form-group">
            {!! Form::submit('Delete Post', ['class'=>'btn btn-danger col-sm-1']) !!}
        </div>

        {!! Form::close() !!}
    </div>


    <div class="row">
        @include('includes.form_error')
    </div>
@stop