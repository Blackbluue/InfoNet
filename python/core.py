#!/usr/bin/env python3
"""core module."""
from sqlalchemy import create_engine, select
from sqlalchemy.orm import Session
from python.media_table import Base, table_names
import python.anime_table
import python.manga_table
import python.movie_table

engine = create_engine("sqlite+pysqlite:///:memory:", future=True)
Base.metadata.create_all(engine)

def add_row(table_name, data):
    table = table_names[table_name]
    row = table(**data)
    with Session(engine) as session:
        session.add(row)
        session.commit()
        
def delete_row(table_name, id):
    table = table_names[table_name]
    with Session(engine) as session:
        row = session.get(table, id)
        session.delete(row)
        session.commit()
    
def update_row(table_name, id, data):
    table = table_names[table_name]
    with Session(engine) as session:
        row = session.get(table, id)
        for column in data:
            setattr(row, column, data[column])
        session.commit()

def get_row(table_name, id):
    table = table_names[table_name]
    with Session(engine) as session:
        results = session.get(table, id)
    return results

def search_rows(table_name, criteria):
    table = table_names[table_name]
    with Session(engine) as session:
        results = session.execute(select(table).filter_by(**criteria)).scalars().all()
    return results

def get_table(table_name):
    table = table_names[table_name]
    with Session(engine) as session:
        results = session.execute(select(table)).scalars().all()
    return results

def empty_table(table_name):
    pass

